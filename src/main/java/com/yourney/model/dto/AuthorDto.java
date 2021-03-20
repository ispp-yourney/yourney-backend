package com.yourney.model.projection;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.yourney.model.StatusType;

@Data
@AllArgsConstructor
public class AuthorDto {
    
	private Long id;
	private String username;  
    private String email;
	private String firstName;
	private String lastName; 
}
