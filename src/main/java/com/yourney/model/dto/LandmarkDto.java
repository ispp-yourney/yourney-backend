package com.yourney.model.dto;

import java.time.LocalDateTime;

import com.yourney.model.StatusType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LandmarkDto {

    private long id;

    private String name;

    private String description;

    private Double price;

    private String country;

    private String city;

    private Double latitude;

    private Double longitude;

    // private boolean promoted;

    private String category;

    private String email;

    private String phone;

    private String website;

    private String instagram;

    private String twitter;

    private LocalDateTime deleteDate;

    private LocalDateTime updateDate;

    private LocalDateTime createDate;

    private Long views;

    private StatusType status;
}
