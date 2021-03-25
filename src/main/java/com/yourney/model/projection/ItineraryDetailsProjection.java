package com.yourney.model.projection;

import java.time.LocalDateTime;
import java.util.List;

import com.yourney.model.SeasonType;
import com.yourney.model.StatusType;

public interface ItineraryDetailsProjection {

    String getId();

    String getName();

    List<ActivityDetailsProjection> getActivities();

    String getDescription();

    StatusType getStatus();

    Double getBudget();

    Integer getEstimatedDays();

    LocalDateTime getCreateDate();

    Integer getViews();

    AuthorProjection getAuthor();

    SeasonType getRecommendedSeason();

    ImageProjection getImage();

}
