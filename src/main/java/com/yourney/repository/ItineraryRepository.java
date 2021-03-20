package com.yourney.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yourney.model.Itinerary;
import com.yourney.model.StatusType;
import com.yourney.model.projection.ItineraryProjection;


@Repository
public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {

    @Query("select it from Itinerary it")
	Iterable<ItineraryProjection> findAllItineraryProjections();

    @Query("select it from Itinerary it where it.id=:id")
	Optional<ItineraryProjection> findOneItineraryProjection(@Param("id") long idItinerario);

    Iterable<Itinerary> findAll(Sort sort);
    
    Page<Itinerary> findAll(Pageable pageable);

    Page<Itinerary> findByStatus(StatusType status, Pageable pageable);
}