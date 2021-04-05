package com.yourney.model.projection;

import java.time.LocalDateTime;

public interface ActivityProjection {

    Long getId();

    String getTitle();

    String getDescription();

    Integer getDay();

    LocalDateTime getCreateDate();

    ItineraryProjection getItinerary();

    LandmarkProjection getLandmark();
}
