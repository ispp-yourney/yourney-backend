package com.yourney.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yourney.config.YourneyMessages;
import com.yourney.exceptions.users.DuplicatedEmail;
import com.yourney.exceptions.users.UserNotFound;
import com.yourney.model.users.User;
import com.yourney.service.users.UserService;

import io.swagger.annotations.Api;

@RestController
@Api(tags = "Users")
public class UserRestController {

	private final UserService userService;

	@Autowired
	public UserRestController(final UserService userService) {
		this.userService = userService;
	}

	private ResponseEntity<Object> userNotFoundResponse() {
		return new ResponseEntity<>(YourneyMessages.USER_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/user/{user_id}")
	public ResponseEntity<Object> getUserById(@PathVariable(name = "user_id") Long id) {
		try {
			User user = userService.findUserById(id);
			if (user != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return userNotFoundResponse();
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/user/count_all")
	public ResponseEntity<Object> countUsersFromSystem() {
		try {
			Long userNumber = userService.countAllUsersFromSystem();
			return new ResponseEntity<>(userNumber, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/user")
	public ResponseEntity<Object> updateUser(@RequestBody @Valid User user) {
		try {
			userService.updateUser(user);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (UserNotFound e) {
			return userNotFoundResponse();
		} catch (DuplicatedEmail e) {
			return new ResponseEntity<>(YourneyMessages.USER_DUPLICATED_EMAIL_MESSAGE, HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/user/{user_id}")
	public ResponseEntity<Object> deleteUserById(@PathVariable(name = "user_id") Long id) {
		try {
			userService.deleteUserById(id);
			return new ResponseEntity<>(YourneyMessages.USER_DISABLED_MESSAGE, HttpStatus.OK);
		} catch (UserNotFound e) {
			return new ResponseEntity<>(YourneyMessages.USER_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
