package com.yourney.model.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.yourney.model.SeasonType;
import com.yourney.model.StatusType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItineraryDto {

    private long id;

    @NotBlank(message = "El campo nombre es obligatorio")
    private String name;

    @NotBlank(message = "El campo descripción es obligatorio")
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
