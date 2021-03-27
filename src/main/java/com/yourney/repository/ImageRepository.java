package com.yourney.repository;

import java.util.List;
import java.util.Optional;

import com.yourney.model.Image;
import com.yourney.model.projection.ImageProjection;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

    //List<ImageProjection> findByOrderById();
/*
    @Query("SELECT i FROM Image i WHERE i.imageUrl=:imageUrl")
    Optional<Image> findByImageUrl(@Param("imageUrl") String imageUrl);
*/
}
