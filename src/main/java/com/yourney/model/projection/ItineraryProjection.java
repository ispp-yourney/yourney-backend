package com.yourney.model.projection;


public interface ItineraryProjection {
    
    String getId();
    String getName();
    String getDescription();
    String getViews();
    
    
    String getImageUrl();
    
    String getUsername();

//    List<ActivityProjection> getActivities();
    void setPoints();
}
