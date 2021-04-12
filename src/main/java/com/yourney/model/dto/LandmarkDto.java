package com.yourney.model.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LandmarkDto {

    private Long id;

    @NotBlank(message = "El campo nombre es obligatorio")
    private String name;

    @NotBlank(message = "El campo nombre es obligatorio")
    private String description;

    @Min(value = 0, message = "El precio mínimo es 0")
    private Double price;

    private String country;

    private String city;

    private Double latitude;

    private Double longitude;

    private String category;

    @Email(message = "El campo email no tiene el formato correcto")
    private String email;

    private String phone;

    @URL(message = "El campo website se trata de una URL")
    private String website;

    @URL(message = "El campo instagram se trata de una URL")
    private String instagram;

    @URL(message = "El campo twitter se trata de una URL")
    private String twitter;

    private LocalDateTime createDate;

    private Long views;

    private Long activity;
}
