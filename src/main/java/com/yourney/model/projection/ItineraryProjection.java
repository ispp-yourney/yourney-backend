package com.yourney.model.projection;

import java.util.List;

public interface ItineraryProjection {
    
    String getId();
    String getName();

    List<ActivityProjection> getActivities();
}
