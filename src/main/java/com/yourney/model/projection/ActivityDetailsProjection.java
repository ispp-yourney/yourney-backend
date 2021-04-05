package com.yourney.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ActivityDetailsProjection {

    Long getId();

    Long getDay();

    String getTitle();

    String getDescription();

    @Value("#{(target.landmark != null)?target.landmark.city:null}")
    String getLandmarkCity();

    @Value("#{(target.landmark != null)?target.landmark.country:null}")
    String getLandmarkCountry();

    @Value("#{(target.landmark != null)?target.landmark.name:null}")
    String getLandmarkName();

    @Value("#{(target.landmark != null)?target.landmark.description:null}")
    String getLandmarkDescription();

    @Value("#{(target.landmark != null)?target.landmark.category:null}")
    String getLandmarkCategory();

    @Value("#{(target.landmark != null)?target.landmark.price:null}")
    Double getLandmarkPrice();

    @Value("#{(target.landmark != null)?target.landmark.email:null}")
    String getLandmarkEmail();

    @Value("#{(target.landmark != null)?target.landmark.phone:null}")
    String getLandmarkPhone();

    @Value("#{(target.landmark != null)?target.landmark.instagram:null}")
    String getLandmarkInstagram();

    @Value("#{(target.landmark != null)?target.landmark.twitter:null}")
    String getLandmarkTwitter();
}
