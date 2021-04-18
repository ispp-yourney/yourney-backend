package com.yourney.security.model.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class LoginUser {

	@Length(max=50, message="No se permiten más de 50 caracteres")
	@NotBlank(message = "El nombre de usuario es obligatorio")
	private String username;

	@Length(max=50, message="No se permiten más de 50 caracteres")
	@NotBlank(message = "La contraseña es obligatoria")
	private String password;

}
