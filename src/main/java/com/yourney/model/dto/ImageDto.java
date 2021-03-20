package com.yourney.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.glassfish.jersey.server.Uri;
import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto {

	@NotBlank
	private String name;

	@NotBlank
	@URL
	private String imageUrl;

}
