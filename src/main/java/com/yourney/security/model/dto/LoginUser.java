package com.yourney.security.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginUser {

	@NotBlank(message = "El nombre de usuario es obligatorio")
	private String username;

	@NotBlank(message = "La contraseña es obligatoria")
	private String password;

}
