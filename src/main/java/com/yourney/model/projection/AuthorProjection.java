package com.yourney.model.projection;

public interface AuthorProjection {

    String getId();

    String getUsername();

    String getEmail();

    String getFirstName();

    String getLastName();

    String getImageUrl();

    ImageProjection getImage();
}
