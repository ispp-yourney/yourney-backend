package com.yourney.model.projection;

import com.yourney.model.StatusType;

public interface ItineraryProjection {

    Long getId();

    String getName();

    String getDescription();

    Integer getViews();

    String getImageUrl();

    String getUsername();

    Double getBudget();

    StatusType getStatus();

    Double getAvgRating();
    
    Long getCountComments();
}
