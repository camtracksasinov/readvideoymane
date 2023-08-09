package com.camtrack.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.camtrack.entities.User;
import com.camtrack.user.repository.UsersRepository;

@Component
public class CustomLoginSuccessHandler {

	@Autowired
	UserServices userService;
	@Autowired
	UsersRepository usersR;

	public void onAuthenticationSuccess(String username) {
		User user = usersR.findByUserName(username).orElse(null);
		if (user.getFailedAttempt() > 0) {
			userService.resetFailedAttempts(user.getEmailid());
		}

	}

}