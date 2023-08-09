//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.camtrack.security.errors.CustomOauthException;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	public static int accessTokenValiditySeconds() {
		return 14400;
	}

	public static int refreshTokenValiditySeconds() {
		return 144000;
	}

	@Autowired
	AuthenticationManager authMgr;
	@Autowired
	DataSource ds;

	@Value("${apps.security.access.token.validity}")
	private Integer security_access_token_validity;

	@Autowired
	private UserDetailsService usrSvc;

	@Bean
	public DefaultAccessTokenConverter accessTokenConverter() {
		return new DefaultAccessTokenConverter();
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(this.tokenStore());
		endpoints.authenticationManager(this.authMgr);
		endpoints.userDetailsService(this.usrSvc);
		endpoints.tokenEnhancer(this.tokenEnhancer());
		endpoints.accessTokenConverter(this.accessTokenConverter());
		endpoints.tokenServices(this.tokenServices());
		endpoints.exceptionTranslator(exception -> {
			if (exception instanceof OAuth2Exception) {
				final OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
				return ResponseEntity.status(HttpStatus.OK)
						.body(new CustomOauthException(oAuth2Exception.getMessage()));
			}
			throw exception;
		});
	}

	/**
	 * @Bean({ "clientPasswordEncoder" }) PasswordEncoder clientPasswordEncoder() {
	 * return (PasswordEncoder)new BCryptPasswordEncoder(8); }
	 */
	/**
	 * public void configure(final AuthorizationServerSecurityConfigurer cfg) throws
	 * Exception { final UrlBasedCorsConfigurationSource source = new
	 * UrlBasedCorsConfigurationSource(); final CorsConfiguration config = new
	 * CorsConfiguration(); config.addAllowedOrigin("*");
	 * config.addAllowedHeader("*"); config.addAllowedMethod("*");
	 * config.setMaxAge(600L); source.registerCorsConfiguration("/**", config);
	 * final CorsFilter filter = new CorsFilter((CorsConfigurationSource)source);
	 * cfg.addTokenEndpointAuthenticationFilter((Filter)filter);
	 * cfg.checkTokenAccess("permitAll");
	 * cfg.passwordEncoder(this.clientPasswordEncoder()); }
	 */

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(this.ds);
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenConverter();
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(this.tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setTokenEnhancer(this.tokenEnhancer());

		defaultTokenServices.setAccessTokenValiditySeconds(security_access_token_validity);
		defaultTokenServices.setRefreshTokenValiditySeconds(security_access_token_validity * 2);
		return defaultTokenServices;
	}

	/**
	 * @Autowired private ClientDetailsService clientDetails;
	 */

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(this.ds);
	}
}
