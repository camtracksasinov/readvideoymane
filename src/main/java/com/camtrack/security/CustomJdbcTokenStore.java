package com.camtrack.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * TokenStore personnalisé qui gère l'incompatibilité de serialVersionUID
 * lors d'une migration de Spring Boot 2.5.x vers 2.7.x.
 *
 * PROBLÈME :
 *   Spring Security 5.5.x (Boot 2.5.3) : SimpleGrantedAuthority serialVersionUID = 570
 *   Spring Security 5.7.x (Boot 2.7.18) : SimpleGrantedAuthority serialVersionUID = 550
 *
 *   Les tokens OAuth2 sont stockés sérialisés en base (JdbcTokenStore).
 *   Après migration, la désérialisation échoue avec :
 *   InvalidClassException: local class incompatible: stream classdesc serialVersionUID=570,
 *                                                    local class serialVersionUID=550
 *
 * SOLUTION :
 *   Surcharger readAuthentication() pour intercepter l'erreur de désérialisation,
 *   supprimer le token invalide de la base, et retourner null.
 *   Résultat : l'utilisateur est simplement invité à se reconnecter (nouveau token généré).
 *   L'application ne plante pas.
 *
 *   Après que tous les anciens tokens ont expiré ou été remplacés,
 *   ce CustomJdbcTokenStore peut être remplacé par un JdbcTokenStore standard.
 */
@Slf4j
public class CustomJdbcTokenStore extends JdbcTokenStore {

    public CustomJdbcTokenStore(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Lit l'authentification associée à un token d'accès.
     * En cas d'erreur de désérialisation (incompatibilité serialVersionUID),
     * supprime le token invalide et retourne null pour forcer une reconnexion.
     */
    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        try {
            return super.readAuthentication(token);
        } catch (IllegalArgumentException e) {
            if (isSerialVersionIncompatibility(e)) {
                log.warn("Token invalide (serialVersionUID incompatible après migration Spring Boot) " +
                         "- suppression et reconnexion requise pour tokenValue={}",
                         maskToken(token.getValue()));
                removeAccessToken(token);
                return null;
            }
            throw e;
        }
    }

    /**
     * Lit l'authentification associée à une valeur de token.
     */
    @Override
    public OAuth2Authentication readAuthentication(String tokenValue) {
        try {
            return super.readAuthentication(tokenValue);
        } catch (IllegalArgumentException e) {
            if (isSerialVersionIncompatibility(e)) {
                log.warn("Token invalide (serialVersionUID incompatible) - suppression tokenValue={}",
                         maskToken(tokenValue));
                removeAccessToken(tokenValue);
                return null;
            }
            throw e;
        }
    }

    /**
     * Lit l'authentification associée à un refresh token.
     */
    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken refreshToken) {
        try {
            return super.readAuthenticationForRefreshToken(refreshToken);
        } catch (IllegalArgumentException e) {
            if (isSerialVersionIncompatibility(e)) {
                log.warn("Refresh token invalide (serialVersionUID incompatible) - suppression");
                removeRefreshToken(refreshToken);
                return null;
            }
            throw e;
        }
    }

    /**
     * Lit un token d'accès pour une authentification.
     * Gère aussi les erreurs de désérialisation sur readAccessToken.
     */
    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        try {
            return super.readAccessToken(tokenValue);
        } catch (IllegalArgumentException e) {
            if (isSerialVersionIncompatibility(e)) {
                log.warn("Lecture token impossible (serialVersionUID incompatible) - suppression tokenValue={}",
                         maskToken(tokenValue));
                removeAccessToken(tokenValue);
                return null;
            }
            throw e;
        }
    }

    /**
     * Vérifie si l'exception est causée par une incompatibilité de serialVersionUID.
     */
    private boolean isSerialVersionIncompatibility(Exception e) {
        Throwable cause = e.getCause();
        while (cause != null) {
            if (cause instanceof java.io.InvalidClassException) {
                String msg = cause.getMessage();
                // Couvre SimpleGrantedAuthority et toute autre classe Spring Security
                if (msg != null && msg.contains("serialVersionUID")) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * Masque un token pour les logs (affiche seulement les 8 premiers caractères).
     */
    private String maskToken(String token) {
        if (token == null || token.length() <= 8) return "***";
        return token.substring(0, 8) + "***";
    }
}