//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.noauths.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

public interface NoauthInterface {
	ResponseEntity<?> chpasswd(String username, String oldpwd, String newpwd) throws Exception;

	ResponseEntity<?> chpasswd2(String username, String oldpwd, String newpwd) throws Exception;

	ResponseEntity<?> chpasswd3expire(String randoms, String oldpwd, String newpwd) throws Exception;

	// ResponseEntity<?> initlostpassword(final String username);

	ResponseEntity<?> initlostpassword2(String is1);

	ResponseEntity<?> login(String username, String password, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	ResponseEntity<?> lostpassword(final String username, final String newpassword, final Long linkid);

	ResponseEntity<?> chpasswd0(String randoms, String newpwd) throws Exception;
}
