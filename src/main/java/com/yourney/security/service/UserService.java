package com.yourney.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourney.security.model.User;
import com.yourney.security.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;

	public Optional<User> getOneById(long id) {
		return userRepository.findById(id);
	}

	public Optional<User> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public User save(User user) {
		User newUser = null;
		try {
            newUser = userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return newUser;
	}

	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
