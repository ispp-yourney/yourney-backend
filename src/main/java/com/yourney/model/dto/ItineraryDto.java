package com.yourney.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.yourney.model.StatusType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItineraryDto {
    
    private long id;

    private String name;
	private String description;
    private StatusType	status;
    private Double budget;
	private Integer	estimatedDays;  
    private List<Long> activities;
    private LocalDateTime deleteDate;
	private LocalDateTime updateDate;
    private LocalDateTime createDate; 
    private Integer	views;
    private Long author;    
}
