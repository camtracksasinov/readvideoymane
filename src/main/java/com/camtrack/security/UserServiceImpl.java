package com.camtrack.security;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.camtrack.entities.User;
import com.camtrack.user.repository.UsersRepository;

@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	UsersRepository userRepository;

	@Override
	public MyUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(s).orElse(null);
		if (Objects.isNull(user))
			throw new UsernameNotFoundException(s);
		return new MyUserDetails(user);
	}
}
