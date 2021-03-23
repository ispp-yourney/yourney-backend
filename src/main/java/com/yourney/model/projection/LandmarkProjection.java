package com.yourney.model.projection;

import java.time.LocalDateTime;

import com.yourney.model.Landmark;

public interface LandmarkProjection {
    
    Long getId();
    String getName();
    String getDescription();
    Double getPrice();
    String getCountry();
    String getCity();
    Double getLatitude();
    Double getLongitude();
    //boolean getPromoted();
    String getCategory();
    String getEmail();
    String getPhone();
    String getWebsite();
    String getInstagram();
    String getTwitter();
    LocalDateTime getDeleteDate();
    LocalDateTime getUpdateDate(); 
    LocalDateTime getCreateDate(); 
    Long getViews();
    
}
