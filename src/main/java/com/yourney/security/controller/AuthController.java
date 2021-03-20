package com.yourney.security.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.yourney.model.dto.Message;
import com.yourney.security.jwt.JwtProvider;
import com.yourney.security.model.Role;
import com.yourney.security.model.RoleType;
import com.yourney.security.model.User;
import com.yourney.security.model.dto.JwtDto;
import com.yourney.security.model.dto.LoginUser;
import com.yourney.security.model.dto.NewUser;
import com.yourney.security.service.RoleService;
import com.yourney.security.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@PostMapping("/new")
	public ResponseEntity<Object> newUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Message("Binding error"), HttpStatus.BAD_REQUEST);
		}
		
		if (userService.existsByUsername(newUser.getUsername())) {
			return new ResponseEntity<>(new Message("Existing username"), HttpStatus.BAD_REQUEST);
		}
		
		if (userService.existsByEmail(newUser.getEmail())) {
			return new ResponseEntity<>(new Message("Existing email"), HttpStatus.BAD_REQUEST);
		}
		
		User user = new User(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()), newUser.getEmail(), newUser.getFirstName(), newUser.getLastName());
		Set<Role> roles = new HashSet();
		roles.add(roleService.getByRoleType(RoleType.ROLE_USER).get());
		if (newUser.getRoles().contains("admin")) {
			roles.add(roleService.getByRoleType(RoleType.ROLE_ADMIN).get());
		}
		
		user.setRoles(roles);
		userService.save(user);
		
		return new ResponseEntity<>(new Message("Created user"), HttpStatus.CREATED);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid LoginUser loginUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Message("Binding error"), HttpStatus.BAD_REQUEST);
		}
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), Collections.emptyList()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<>(jwtDto, HttpStatus.CREATED);
	}
}
