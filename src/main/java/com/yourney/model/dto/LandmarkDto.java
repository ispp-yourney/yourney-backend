package com.yourney.model.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LandmarkDto {

    private Long id;

    @NotBlank(message = "El campo nombre es obligatorio")
    @Length(max = 100)
    private String name;

    @NotBlank(message = "El campo nombre es obligatorio")
    @Length(max = 255)
    private String description;

    @Min(value = 0, message = "El precio mínimo es 0")
    private Double price;
    
    @NotBlank(message = "El campo país es obligatorio")
    private String country;

    @NotBlank(message = "El campo ciudad es obligatorio")
    private String city;
    
    @Range(min = -90, max = 90)
    private Double latitude;

    @Range(min = -180, max = 180)
    private Double longitude;

    private String category;
    
    @Email
    private String email;

    @Length(max = 50)
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
