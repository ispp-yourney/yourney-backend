package com.yourney.model.dto;

import javax.validation.constraints.NotBlank;

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
