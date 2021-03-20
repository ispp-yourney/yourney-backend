package com.yourney.model.dto;

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

}
