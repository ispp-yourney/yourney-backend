package com.yourney.model.projection;

import com.yourney.model.ContactInfo;

import org.springframework.beans.factory.annotation.Value;

public interface ActivityDetailsProjection {
    
    Long getId();
    Long getDay();

    String getTitle();
    String getDescription();
    
    @Value("#{target.landmark.city}")
    String getLandmarkCity();

    @Value("#{target.landmark.country}")
    String getLandmarkCountry();

    @Value("#{target.landmark.name}")
    String getLandmarkName();

    @Value("#{target.landmark.description}")
    String getLandmarkDescription();

    @Value("#{target.landmark.price}")
    Double getLandmarkPrice();

    @Value("#{target.landmark.contactInfo}")
    ContactInfo getContactInfo();
}
