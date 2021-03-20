package com.yourney.repository;

import java.util.Optional;

import com.yourney.model.Itinerary;
import com.yourney.model.projection.ItineraryProjection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {

    @Query("select it from Itinerary it")
	Iterable<ItineraryProjection> findAllItineraryProjections();

    @Query("select it from Itinerary it where it.id=:id")
	Optional<ItineraryProjection> findOneItineraryProjection(@Param("id") long idItinerario);

}