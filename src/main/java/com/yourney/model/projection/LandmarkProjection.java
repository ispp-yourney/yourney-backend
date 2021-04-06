package com.yourney.model.projection;

import java.time.LocalDateTime;

public interface LandmarkProjection {

    Long getId();

    String getName();

    String getDescription();

    Double getPrice();

    String getCountry();

    String getCity();

    Double getLatitude();

    Double getLongitude();

    String getCategory();

    String getEmail();

    String getPhone();

    String getWebsite();

    String getInstagram();

    String getTwitter();

    LocalDateTime getCreateDate();

    Long getViews();

    ImageProjection getImage();
}
