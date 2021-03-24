package com.yourney.model.projection;

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

    @Value("#{target.landmark.category}")
    String getLandmarkCategory();

    @Value("#{target.landmark.price}")
    Double getLandmarkPrice();

    @Value("#{target.landmark.email}")
    String getLandmarkEmail();

    @Value("#{target.landmark.phone}")
    String getLandmarkPhone();

    @Value("#{target.landmark.instagram}")
    String getLandmarkInstagram();

    @Value("#{target.landmark.twitter}")
    String getLandmarkTwitter();
}
