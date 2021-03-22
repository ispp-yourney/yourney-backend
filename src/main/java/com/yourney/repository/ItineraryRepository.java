package com.yourney.repository;

import java.util.Optional;


import java.util.List;


import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yourney.model.Itinerary;
import com.yourney.model.StatusType;
import com.yourney.model.projection.ItineraryDetailsProjection;
import com.yourney.model.projection.ItineraryProjection;


@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    @Query("select it from Itinerary it where it.status = 'PUBLISHED'")
	Iterable<Itinerary> findAllItinerary();
    
    @Query("select it from Itinerary it where it.status = 'PUBLISHED'")
	Iterable<ItineraryProjection> findAllItineraryProjections();
    
    @Query("select it from Itinerary it where it.status = 'PUBLISHED' order by points desc, views desc, create_date desc")
	List<ItineraryProjection> findAllItineraryProjectionsOrdered(Pageable pageable);
    
    @Query("select it from Itinerary it where it.status = 'PUBLISHED' and LOWER(it.name) LIKE %:cadena% order by points desc, views desc, create_date desc")
	List<ItineraryProjection> findSearchItineraryProjectionsOrdered(Pageable pageable, String cadena);
    
    @Query("select it from Itinerary it where it.status = 'PUBLISHED' and LOWER(it.name) LIKE %:cadena%")
	List<Itinerary> findSearchItinerary(String cadena);
    
    @Query("select it from Itinerary it where it.status = 'PUBLISHED' and it.author.id =:userId order by points desc, views desc, create_date desc")
	List<ItineraryProjection> findUserItineraryProjectionsOrdered(Pageable pageable, Long userId);
    
    @Query("select it from Itinerary it where it.status = 'PUBLISHED' and it.author.id =:userId")
	List<Itinerary> findUserItinerary(Long userId);

    @Query("select it from Itinerary it where it.id=:id")
	Optional<ItineraryProjection> findOneItineraryProjection(@Param("id") long idItinerario);

    @Query("select it from Itinerary it where it.id=:id")
	Optional<ItineraryDetailsProjection> findOneItineraryDetailsProjection(@Param("id") long idItinerario);


    Page<Itinerary> findByStatus(StatusType status, Pageable pageable);
    
    Page<Itinerary> findByActivitiesLandmarkCountry(String country, Pageable pageable);
    
 
}