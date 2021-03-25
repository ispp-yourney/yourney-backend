package com.yourney.model.projection;

public interface ItineraryProjection {

    Long getId();

    String getName();

    String getDescription();

    Integer getViews();

    String getImageUrl();

    String getUsername();

    Double getBudget();

    // List<ActivityProjection> getActivities();
    // StatusType getStatus();
    // Integer getEstimatedDays();
    // LocalDateTime getCreateDate();
    // AuthorProjection getAuthor();
    // List<SeasonProjection> getRecommendedSeasons();
    // ImageProjection getImage();

}
