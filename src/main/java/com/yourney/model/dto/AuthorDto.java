package com.yourney.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDto {

	private Long id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
}
