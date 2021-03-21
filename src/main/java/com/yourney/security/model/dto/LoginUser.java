package com.yourney.security.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginUser {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
}
