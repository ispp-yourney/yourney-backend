package com.yourney.repository;

import java.util.Optional;

import com.yourney.model.Activity;
import com.yourney.model.projection.ActivityProjection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {

    @Query("select ac from Activity ac where ac.id=:id")
	Optional<ActivityProjection> findOneActivityProjection(@Param("id") long idActivity);

    @Query("select ac from Activity ac")
	Iterable<ActivityProjection> findAllActivityProjections();

    @Query("select ac from Activity ac where ac.day=:dia and ac.itinerary.id=:id")
	Iterable<ActivityProjection> findAllActivityProjectionsByDayAndItinerary(@Param("id") long idItinerary, @Param("dia") int dia);
	
}