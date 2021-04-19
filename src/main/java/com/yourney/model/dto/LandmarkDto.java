package com.yourney.model.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandmarkDto {

    private Long id;

    @NotBlank(message = "El campo nombre es obligatorio")
    @Length(max = 50, message = "El tamaño del campo nombre es demasiado largo, y el máximo son 50 caracteres.")
    private String name;

    @NotBlank(message = "El campo nombre es obligatorio")
    @Length(max = 1000, message = "El tamaño del campo descripción es demasiado largo, y el máximo son 1000 caracteres.")
    private String description;

    @Min(value = 0, message = "El precio mínimo es 0")
    @Max(value = 10000, message = "El precio máximo es 10000")
    private Double price;

    @NotBlank(message = "El campo país es obligatorio")
    @Length(max = 100, message = "El campo país es demasiado largo")
    private String country;

    @NotBlank(message = "El campo ciudad es obligatorio")
    @Length(max = 100, message = "El campo ciudad es demasiado largo")
    private String city;

    @Range(min = -90, max = 90, message = "La latitud debe encontrarse entre -90 y 90")
    private Double latitude;

    @Range(min = -180, max = 180, message = "La longitud debe encontrarse entre -180 y 180")
    private Double longitude;

    @Length(max = 50, message = "El campo category es demasiado largo")
    private String category;

    @Email(message = "El campo email no tiene el formato correcto")
    @Length(max = 100, message = "El campo email es demasiado largo")
    private String email;

    @Pattern(regexp = "^(([+][(][0-9]{1,3}[)][ ])?([0-9]{6,12}))$", message = "El teléfono no tiene un formato válido")
    @Length(max = 50, message = "El campo teléfono es demasiado largo")
    private String phone;

    @URL(message = "El campo website se trata de una URL")
    @Length(max = 300, message = "El campo website es demasiado largo")
    private String website;

    @URL(message = "El campo instagram se trata de una URL")
    @Length(max = 300, message = "El campo instagram es demasiado largo")
    private String instagram;

    @URL(message = "El campo twitter se trata de una URL")
    @Length(max = 300, message = "El campo twitter es demasiado largo")
    private String twitter;

    private LocalDateTime createDate;

    private Long views;

    private Long activity;
}
