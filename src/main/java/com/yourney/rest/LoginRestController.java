package com.yourney.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yourney.exceptions.users.DuplicatedEmail;
import com.yourney.exceptions.users.DuplicatedUsername;
import com.yourney.model.users.User;
import com.yourney.service.users.UserService;

import io.swagger.annotations.Api;

@RestController
@Api(tags = "Login")
public class LoginRestController {

	private final UserService userService;

	@Autowired
	public LoginRestController(final UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/login")
	public ResponseEntity<Object> login(@RequestParam String username,
			@RequestParam(name = "password") String encryptedPassword) {
		
		try {
			if (userService.validatePasswordFromUsernameOrEmail(username, encryptedPassword)) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/register")
	public ResponseEntity<Object> register(@RequestBody @Valid User user) {
		try {
			userService.addUser(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (DuplicatedUsername e) {
			String message = "Username is duplicated";
			return new ResponseEntity<>(message, HttpStatus.CONFLICT);
		} catch (DuplicatedEmail e) {
			String message = "Email is duplicated";
			return new ResponseEntity<>(message, HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
