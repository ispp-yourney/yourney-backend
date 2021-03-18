package com.yourney.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginUser {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
}
