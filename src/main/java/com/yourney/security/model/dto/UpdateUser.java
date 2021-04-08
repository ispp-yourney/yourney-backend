package com.yourney.security.model.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UpdateUser {

	@NotBlank(message = "El nombre es obligatorio")
	private String firstName;

	@NotBlank(message = "Los apellidos son obligatorios")
	private String lastName;

	@Email(message = "El correo electrónico introducido no es válido")
	@NotBlank(message = "El correo electrónico es obligatorio")
	private String email;

}
