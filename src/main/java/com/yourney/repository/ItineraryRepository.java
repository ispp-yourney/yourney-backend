package com.yourney.repository;

import com.yourney.model.Itinerary;
import com.yourney.model.projection.ItineraryProjection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {

    @Query("select it from Itinerary it where it.status = 'PUBLISHED'")
	Iterable<ItineraryProjection> findAllItineraryProjections();
    
    @Query("select it from Itinerary it where it.status = 'PUBLISHED' order by points desc, views desc, create_date desc")
	Iterable<ItineraryProjection> findAllItineraryProjectionsOrdered();

}