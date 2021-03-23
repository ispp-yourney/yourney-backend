package com.yourney.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.yourney.model.Activity;
import com.yourney.model.Landmark;
import com.yourney.model.projection.LandmarkProjection;


@Repository
public interface LandmarkRepository extends CrudRepository<Landmark, Long> {
	
    @Query("select it from Landmark it where it.id=:id")
	Optional<LandmarkProjection> findOneLandmarkProjection(@Param("id") long idLandmark);

    @Query("select a from Activity a where a.landmark.id=:id")
	Optional<Activity> findOneActivityByLandmark(@Param("id") long idLandmark);

    @Query("select distinct l.country from Landmark l order by country")
	Iterable<String> findAllCountries();
    
    @Query("select distinct l.city from Landmark l where l.country=:name order by city")
    Iterable<String> findCitiesByCountry(@Param("name") String name);

    @Query("select distinct l.city from Landmark l order by city")
	Iterable<String> findAllCities();
 

	
}