//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.security.errors;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
	public CustomOauthException(final String msg) {
		super(msg);
	}
}
