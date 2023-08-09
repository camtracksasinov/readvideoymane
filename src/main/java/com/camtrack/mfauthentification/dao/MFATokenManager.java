package com.camtrack.mfauthentification.dao;

import dev.samstevens.totp.exceptions.QrGenerationException;

public interface MFATokenManager {
	String generateSecretKey();

	String getQRCode(final String secret, String username) throws QrGenerationException;

	boolean verifyTotp(final String code, final String secret);
}