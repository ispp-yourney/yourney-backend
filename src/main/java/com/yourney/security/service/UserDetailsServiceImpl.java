package com.yourney.security.service;

import com.yourney.security.model.PrincipalUser;
import com.yourney.security.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
		return PrincipalUser.build(user);
	}
}
