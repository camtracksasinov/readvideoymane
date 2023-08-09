//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.config;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encryption {
	private static final String ALGORITHM = "AES";
	private static final String KEY = "$keyfor$quoteapp";

	public static String decodeAndDecrypt(final String value) throws Exception {
		final String decoded = new String(Base64.decodeBase64(value));
		final Key key = generateKey();
		final Cipher cipher = Cipher.getInstance("AES");
		cipher.init(2, key);
		final byte[] decryptedValue64 = Base64.decodeBase64(decoded);
		final byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
		final String decryptedValue65 = new String(decryptedByteValue, "utf-8");
		return decryptedValue65;
	}

	public static String decrypt(final String value) throws Exception {
		final Key key = generateKey();
		final Cipher cipher = Cipher.getInstance("AES");
		cipher.init(2, key);
		final byte[] decryptedValue64 = Base64.decodeBase64(value);
		final byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
		final String decryptedValue65 = new String(decryptedByteValue, "utf-8");
		return decryptedValue65;
	}

	public static String encrypt(final String value) throws Exception {
		final Key key = generateKey();
		final Cipher cipher = Cipher.getInstance("AES");
		cipher.init(1, key);
		final byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
		final String encryptedValue64 = Base64.encodeBase64String(encryptedByteValue);
		return encryptedValue64;
	}

	public static String encryptAndEncode(final String value) throws Exception {
		final Key key = generateKey();
		final Cipher cipher = Cipher.getInstance("AES");
		cipher.init(1, key);
		final byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
		final String encryptedValue64 = Base64.encodeBase64String(encryptedByteValue);
		final String encoded = Base64.encodeBase64String(encryptedValue64.getBytes());
		return encoded;
	}

	private static Key generateKey() throws Exception {
		final Key key = new SecretKeySpec("$keyfor$quoteapp".getBytes(), "AES");
		return key;
	}
}
