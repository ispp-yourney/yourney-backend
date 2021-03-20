package com.yourney.model.projection;

import java.time.LocalDateTime;
import java.util.List;

import com.yourney.model.StatusType;

public interface AuthorProjection {
    
    String getId();
	String getUsername();    
    String getEmail();
    String getFirstName();
    String getLastName();  
}
