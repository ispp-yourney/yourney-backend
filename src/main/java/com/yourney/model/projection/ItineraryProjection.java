package com.yourney.model.projection;


import java.time.LocalDateTime;
import java.util.List;


import com.yourney.model.StatusType;

public interface ItineraryProjection {
    
    Long getId();
    String getName();

    String getDescription();
    Integer getViews();
   
    String getImageUrl(); 
    String getUsername();

    void setPoints();

    //List<ActivityProjection> getActivities();
	  //String getDescription();
    //StatusType	getStatus();
    //Double getBudget();
	  //Integer	getEstimatedDays();  
    //LocalDateTime getDeleteDate();
	  //LocalDateTime getUpdateDate(); 
    //LocalDateTime getCreateDate(); 
    //Integer getViews();
    //AuthorProjection getAuthor();
    //List<SeasonProjection> getRecommendedSeasons();    
    //ImageProjection getImage();   

}
