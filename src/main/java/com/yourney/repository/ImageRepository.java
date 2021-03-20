package com.yourney.repository;

import java.util.List;

import com.yourney.model.Image;
import com.yourney.model.projection.ImageProjection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    
    List<ImageProjection> findByOrderById();
    
}
