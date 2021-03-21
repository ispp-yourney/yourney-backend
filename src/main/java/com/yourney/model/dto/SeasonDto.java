package com.yourney.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeasonDto {
    
    private long id;

    private String recommendedSeasons;

}
