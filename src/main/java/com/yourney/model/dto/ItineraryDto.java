package com.yourney.model.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.yourney.model.SeasonType;
import com.yourney.model.StatusType;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItineraryDto {

    private long id;

    @NotBlank(message = "El campo nombre es obligatorio")
    @Length(max = 50, message = "El tamaño del campo nombre es demasiado largo, y el máximo son 50 caracteres.")
    private String name;

    @NotBlank(message = "El campo descripción es obligatorio")
    @Length(max = 1000, message = "El tamaño del campo descripción es demasiado largo, y el máximo son 1000 caracteres.")
    private String description;

    @NotNull(message = "El campo presupuesto es obligatorio")
    @Min(value = 0, message = "El presupuesto mínimo es 0")
    private Double budget;

    @NotNull(message = "El campo días estimados es obligatorio")
    @Min(value = 1, message = "La duración estimada mínima es 1")
    private Integer estimatedDays;

    private LocalDateTime createDate;

    private SeasonType recommendedSeason;

    private StatusType status;

}
