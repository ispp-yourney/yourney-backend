package com.yourney.model.projection;

import java.time.LocalDateTime;

import com.yourney.model.Landmark;

public interface ActivityProjection {
    
    Long getId();
    String getTitle();
    String getDescription();
    Integer getDay();
    LocalDateTime getDeleteDate();
    LocalDateTime getUpdateDate();
    LocalDateTime getCreateDate();
    Integer getViews();  
    ItineraryProjection getItinerary(); 
    LandmarkProjection getLandmark();
}
