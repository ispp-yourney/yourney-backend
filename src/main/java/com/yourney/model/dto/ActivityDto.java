package com.yourney.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityDto {

	private Long id;

	private String title;

	private String description;

	private Integer day;

    private Long itineraryId;

	private LocalDateTime deleteDate;
	
	private LocalDateTime updateDate;
    
	private LocalDateTime createDate; 

	private Long landmarkId;

    private Integer	views;	
}
