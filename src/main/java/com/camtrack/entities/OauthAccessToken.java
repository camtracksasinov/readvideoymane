//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "oauth_access_token")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "OauthAccessToken.findAll", query = "SELECT o FROM OauthAccessToken o"),
		@NamedQuery(name = "OauthAccessToken.findByTokenId", query = "SELECT o FROM OauthAccessToken o WHERE o.tokenId = :tokenId"),
		@NamedQuery(name = "OauthAccessToken.findByAuthenticationId", query = "SELECT o FROM OauthAccessToken o WHERE o.authenticationId = :authenticationId"),
		@NamedQuery(name = "OauthAccessToken.findByUserName", query = "SELECT o FROM OauthAccessToken o WHERE o.userName = :userName"),
		@NamedQuery(name = "OauthAccessToken.findByClientId", query = "SELECT o FROM OauthAccessToken o WHERE o.clientId = :clientId"),
		@NamedQuery(name = "OauthAccessToken.findByRefreshToken", query = "SELECT o FROM OauthAccessToken o WHERE o.refreshToken = :refreshToken") })
public class OauthAccessToken implements Serializable {
	private static final long serialVersionUID = 1L;
	@Lob
	@Column(name = "authentication")
	private byte[] authentication;
	@Id
	@Basic(optional = false)
	@Column(name = "authentication_id")
	private String authenticationId;
	@Column(name = "client_id")
	private String clientId;
	@Column(name = "refresh_token")
	private String refreshToken;
	@Lob
	@Column(name = "token")
	private byte[] token;
	@Column(name = "token_id")
	private String tokenId;
	@Column(name = "user_name")
	private String userName;

	public OauthAccessToken() {
	}

	public OauthAccessToken(final String authenticationId) {
		this.authenticationId = authenticationId;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof OauthAccessToken)) {
			return false;
		}
		final OauthAccessToken other = (OauthAccessToken) object;
		return (this.authenticationId != null || other.authenticationId == null)
				&& (this.authenticationId == null || this.authenticationId.equals(other.authenticationId));
	}

	public byte[] getAuthentication() {
		return this.authentication;
	}

	public String getAuthenticationId() {
		return this.authenticationId;
	}

	public String getClientId() {
		return this.clientId;
	}

	public String getRefreshToken() {
		return this.refreshToken;
	}

	public byte[] getToken() {
		return this.token;
	}

	public String getTokenId() {
		return this.tokenId;
	}

	public String getUserName() {
		return this.userName;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.authenticationId != null) ? this.authenticationId.hashCode() : 0);
		return hash;
	}

	public void setAuthentication(final byte[] authentication) {
		this.authentication = authentication;
	}

	public void setAuthenticationId(final String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}

	public void setRefreshToken(final String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void setToken(final byte[] token) {
		this.token = token;
	}

	public void setTokenId(final String tokenId) {
		this.tokenId = tokenId;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.OauthAccessToken[ authenticationId=" + this.authenticationId + " ]";
	}
}
