package com.camtrack.mfauthentification.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import dev.samstevens.totp.code.CodeVerifier;
//import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
//import dev.samstevens.totp.qr.QrGenerator;
//import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.util.Utils;

@Service("mfaTokenManager")
public class DefaultMFATokenManager implements MFATokenManager {

	/**
	 * @Autowired private CodeVerifier codeVerifier;
	 */
	@Value("${qrcode.name}")
	private String qrcodename;
	/**
	 * @Autowired private QrGenerator qrGenerator;
	 */
	/**
	 * @Autowired private SecretGenerator secretGenerator;
	 */

	@Autowired
	private TOTPConfig qrGenerators;

	@Override
	public String generateSecretKey() {
		return qrGenerators.secretGenerator().generate();
	}

	@Override
	public String getQRCode(String secret, String username) throws QrGenerationException {

		QrData data = new QrData.Builder().label(username).secret(secret).issuer(qrcodename)
				.algorithm(TOTPConstants.HASHING_ALGO).digits(6).period(30).build();
		return Utils.getDataUriForImage(qrGenerators.qrGenerator().generate(data),
				qrGenerators.qrGenerator().getImageMimeType());
	}

	@Override
	public boolean verifyTotp(String code, String secret) {
		try {
			return qrGenerators.codeVerifier().isValidCode(secret, code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
