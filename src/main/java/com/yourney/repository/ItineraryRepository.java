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

    @Query("select it from Itinerary it where it.status='PUBLISHED' order by it.calcPlan desc, it.views desc")
    Page<ItineraryProjection> findByStatus(StatusType status, Pageable pageable);
    
    @Query("select distinct ac.itinerary.id as id, ac.itinerary.name as name, ac.itinerary.description as description, ac.itinerary.image.imageUrl as imageUrl, ac.itinerary.author.username as username, ac.itinerary.views as views, ac.itinerary.calcPlan as calcPlan, ac.itinerary.calcPromotion as calcPromotion from Activity ac where ac.itinerary.status='PUBLISHED' and ac.landmark.country=:country order by ac.itinerary.calcPlan desc, ac.itinerary.calcPromotion desc, ac.itinerary.views desc")
    Page<ItineraryProjection> findByActivitiesLandmarkCountry(String country, Pageable pageable);

    @Query("select distinct ac.itinerary.id as id, ac.itinerary.name as name, ac.itinerary.description as description, ac.itinerary.image.imageUrl as imageUrl, ac.itinerary.author.username as username, ac.itinerary.views as views, ac.itinerary.calcPlan as calcPlan, ac.itinerary.calcPromotion as calcPromotion from Activity ac where ac.itinerary.status='PUBLISHED' and ac.landmark.city=:city order by ac.itinerary.calcPlan desc, ac.itinerary.calcPromotion desc, ac.itinerary.views desc")
    Page<ItineraryProjection> findByActivitiesLandmarkCity(String city, Pageable pageable);

    final String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(ac.landmark.latitude)) * cos(radians(ac.landmark.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(ac.landmark.latitude))))";
    @Query("select distinct ac.itinerary.id as id, ac.itinerary.name as name, ac.itinerary.description as description, ac.itinerary.image.imageUrl as imageUrl, ac.itinerary.author.username as username, ac.itinerary.views as views, ac.itinerary.calcPlan as calcPlan, " + HAVERSINE_FORMULA + " as distance, ac.itinerary.calcPromotion as calcPromotion from Activity ac where ac.itinerary.status='PUBLISHED' order by distance asc, ac.itinerary.calcPlan desc, ac.itinerary.calcPromotion desc, ac.itinerary.views desc")
    Page<ItineraryProjection> findByActivitiesLandmarkDistance(Double latitude, Double longitude, Pageable pageable);
}