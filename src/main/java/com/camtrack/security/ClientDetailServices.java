//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.security;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

public class ClientDetailServices implements ClientDetailsService {
	public static int accessTokenValiditySeconds() {
		return 14400;
	}

	public static int refreshTokenValiditySeconds() {
		return 14400;
	}

	@Override
	public ClientDetails loadClientByClientId(final String clientId) {
		final BaseClientDetails client = new BaseClientDetails();
		client.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds());
		client.setAccessTokenValiditySeconds(accessTokenValiditySeconds());
		return client;
	}
}
