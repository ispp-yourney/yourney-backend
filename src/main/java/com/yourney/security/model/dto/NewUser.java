package com.yourney.security.model.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class NewUser {

	@Length(max=50, message="No se permiten más de 50 caracteres")
	@NotBlank(message = "El nombre de usuario es obligatorio")
	private String username;

	@Length(max=50, message="No se permiten más de 50 caracteres")
	@NotBlank(message = "La contraseña es obligatoria")
	private String password;

	@Length(max=50, message="No se permiten más de 50 caracteres")
	@NotBlank(message = "El nombre es obligatorio")
	private String firstName;

	@Length(max=50, message="No se permiten más de 50 caracteres")
	@NotBlank(message = "Los apellidos son obligatorios")
	private String lastName;

	@Email(message = "El correo electrónico introducido no es válido")
	@NotBlank(message = "El correo electrónico es obligatorio")
	private String email;

	private Set<String> roles = new HashSet<>();

}
