package com.yourney.repository;

import com.yourney.model.Activity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {

    @Query("select ac from Activity ac where ac.day=:dia and ac.itinerary.id=:id")
    Iterable<Activity> findAllActivityProjectionsByDayAndItinerary(@Param("id") long idItinerary,
            @Param("dia") int dia);

    @Query("select ac from Activity ac where ac.itinerary.id=:id")
    Iterable<Activity> findActivityByItinerary(@Param("id") long idItinerary);
}
	