package com.yourney.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.yourney.model.Itinerary;
import com.yourney.model.projection.ItineraryProjection;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    @Query("select distinct ac.itinerary.id as id, ac.itinerary.name as name, ac.itinerary.description as description, ac.itinerary.image.imageUrl as imageUrl, ac.itinerary.author.username as username, ac.itinerary.views as views, ac.itinerary.budget as budget, ac.itinerary.calcPlan as calcPlan, ac.itinerary.calcPromotion as calcPromotion from Activity ac where ac.itinerary.status='PUBLISHED' and LOWER(ac.landmark.country) like LOWER(:country) and LOWER(ac.landmark.city) like LOWER(:city) and ac.itinerary.budget <= :maxBudget order by ac.itinerary.calcPlan desc, ac.itinerary.calcPromotion desc, ac.itinerary.views desc")
    Page<ItineraryProjection> searchByProperties(String country, String city, Double maxBudget, Pageable pageable);

    @Query("select it from Itinerary it where it.status = 'PUBLISHED' and LOWER(it.name) LIKE LOWER(:cadena) order by calcPlan desc, calcPromotion desc, views desc, create_date desc")
    Page<ItineraryProjection> searchByName(Pageable pageable, String cadena);

    final String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(ac.landmark.latitude)) * cos(radians(ac.landmark.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(ac.landmark.latitude))))";

    @Query("select distinct ac.itinerary.id as id, ac.itinerary.name as name, ac.itinerary.description as description, ac.itinerary.image.imageUrl as imageUrl, ac.itinerary.author.username as username, ac.itinerary.views as views, ac.itinerary.calcPlan as calcPlan, "
            + HAVERSINE_FORMULA
            + " as distance, ac.itinerary.calcPromotion as calcPromotion from Activity ac where ac.itinerary.status='PUBLISHED' order by distance asc, ac.itinerary.calcPlan desc, ac.itinerary.calcPromotion desc, ac.itinerary.views desc")
    Page<ItineraryProjection> searchByDistance(Double latitude, Double longitude, Pageable pageable);

    @Query("select it from Itinerary it where it.status = 'PUBLISHED' and it.author.id =:userId order by it.calcPromotion desc, it.views desc")
    Page<ItineraryProjection> searchByUserId(Pageable pageable, Long userId);

    @Query("select it from Itinerary it where it.status = 'PUBLISHED' and it.author.username =:username order by it.calcPromotion desc, it.views desc")
    Page<ItineraryProjection> searchByUsername(Pageable pageable, String username);
}