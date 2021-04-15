package com.yourney.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto {

	private Long id;

	@NotBlank(message = "El campo nombre es obligatorio")
	private String title;

	@NotBlank(message = "El campo nombre es obligatorio")
	private String description;

	@NotNull(message = "El campo días estimados es obligatorio")
	@Min(value = 1, message = "La duración estimada mínima es 1")
	private Integer day;

	private Long itinerary;

	private Long landmark;
}
