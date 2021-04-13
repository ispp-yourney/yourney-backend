package com.yourney.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {

    @NotNull(message = "La id del itinerario es obligatoria")
    private Long itinerary;

    @NotBlank(message = "El campo contenido es obligatorio")
    @Length(max = 1000, message = "El tamaño del campo descripción es demasiado largo, y el máximo son 1000 caracteres.")
    private String content;

    @NotNull(message = "El campo puntuación es obligatorio")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private Integer rating;
    
}
