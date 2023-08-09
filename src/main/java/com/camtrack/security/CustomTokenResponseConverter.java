//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.util.StringUtils;

public class CustomTokenResponseConverter implements Converter<Map<String, String>, OAuth2AccessTokenResponse> {
	private static final Set<String> TOKEN_RESPONSE_PARAMETER_NAMES;

	static {
		TOKEN_RESPONSE_PARAMETER_NAMES = Stream
				.of(new String[] { "access_token", "token_type", "expires_in", "refresh_token", "scope" })
				.collect(Collectors.toSet());
	}

	@Override
	public OAuth2AccessTokenResponse convert(final Map<String, String> tokenResponseParameters) {
		final String accessToken = tokenResponseParameters.get("access_token");
		OAuth2AccessToken.TokenType accessTokenType = null;
		if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(tokenResponseParameters.get("token_type"))) {
			accessTokenType = OAuth2AccessToken.TokenType.BEARER;
		}
		long expiresIn = 0L;
		if (tokenResponseParameters.containsKey("expires_in")) {
			try {
				expiresIn = Long.valueOf(tokenResponseParameters.get("expires_in"));
			} catch (NumberFormatException ex) {
			}
		}
		Set<String> scopes = Collections.emptySet();
		if (tokenResponseParameters.containsKey("scope")) {
			final String scope = tokenResponseParameters.get("scope");
			scopes = Arrays.stream(StringUtils.delimitedListToStringArray(scope, " ")).collect(Collectors.toSet());
		}
		final String refreshToken = tokenResponseParameters.get("refresh_token");
		final Map<String, Object> additionalParameters = new LinkedHashMap<>();
		tokenResponseParameters.entrySet().stream()
				.filter(e -> !CustomTokenResponseConverter.TOKEN_RESPONSE_PARAMETER_NAMES.contains(e.getKey()))
				.forEach(e -> additionalParameters.put(e.getKey(), e.getValue()));
		return OAuth2AccessTokenResponse.withToken(accessToken).tokenType(accessTokenType).expiresIn(expiresIn)
				.scopes(scopes).refreshToken(refreshToken).additionalParameters(additionalParameters).build();
	}
}
