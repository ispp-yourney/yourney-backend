package com.yourney.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto {

	private Long id;

	@NotBlank(message = "El campo nombre es obligatorio")
	@Length(max = 50, message = "El tamaño del campo título es demasiado largo, y el máximo son 50 caracteres.")
	private String title;

	@NotBlank(message = "El campo nombre es obligatorio")
	@Length(max = 1000, message = "El tamaño del campo descripción es demasiado largo, y el máximo son 1000 caracteres.")
	private String description;

	@NotNull(message = "El campo días estimados es obligatorio")
	@Min(value = 1, message = "El mínimo valor del día es 1")
	private Integer day;

	private Long itinerary;

	private Long landmark;
}
