package com.yourney.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.yourney.model.Itinerary;
import com.yourney.model.projection.ItineraryProjection;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    @Query("select distinct i FROM Itinerary i left join i.activities a where i.status='PUBLISHED' and LOWER(a.landmark.country) like LOWER(:country) and LOWER(a.landmark.city) like LOWER(:city) and i.budget <= :maxBudget and i.estimatedDays <= :maxDays order by i.calcPromotion desc, i.calcPlan desc, i.views desc")
    Page<ItineraryProjection> searchByProperties(String country, String city, Double maxBudget, Integer maxDays, Pageable pageable);

    @Query("select it from Itinerary it where it.status = 'PUBLISHED' and LOWER(it.name) LIKE LOWER(:cadena) order by calcPlan desc, calcPromotion desc, views desc, create_date desc")
    Page<ItineraryProjection> searchByName(Pageable pageable, String cadena);

    final String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(a.landmark.latitude)) * cos(radians(a.landmark.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(a.landmark.latitude))))";

    @Query("select distinct i.id as id, i.name as name, i.description as description, i.image.imageUrl as imageUrl, i.author.username as username, i.views as views, i.calcPlan as calcPlan, i.budget as budget, i.status as status, i.avgRating as avgRating, "
            + HAVERSINE_FORMULA
            + " as distance, i.calcPromotion as calcPromotion FROM Itinerary i left join i.activities a where i.status='PUBLISHED' order by distance asc, i.calcPlan desc, i.calcPromotion desc, i.views desc")
    Page<ItineraryProjection> searchByDistance(Double latitude, Double longitude, Pageable pageable);

    @Query("select it from Itinerary it where it.status = 'PUBLISHED' and it.author.id =:userId order by it.calcPromotion desc, it.views desc")
    Page<ItineraryProjection> searchByUserId(Pageable pageable, Long userId);

    @Query("select it from Itinerary it where it.status = 'PUBLISHED' and it.author.username =:username order by it.calcPromotion desc, it.views desc")
    Page<ItineraryProjection> searchByUsername(Pageable pageable, String username);

    @Query("select it from Itinerary it where it.author.username =:username order by it.calcPromotion desc, it.views desc")
    Page<ItineraryProjection> searchByCurrentUsername(Pageable pageable, String username);

    @Query("select distinct i FROM Itinerary i left join i.activities a where i.status='PUBLISHED' and LOWER(a.landmark.country) like LOWER(:country) and LOWER(a.landmark.city) like LOWER(:city) order by i.views desc")
    Page<ItineraryProjection> searchOrderedByViews(String country, String city, Pageable pageable);

    @Query("select distinct i from Itinerary i left join i.activities a where i.status='PUBLISHED' and LOWER(a.landmark.country) like LOWER(:country) and LOWER(a.landmark.city) like LOWER(:city) order by i.countComments desc")
    Page<ItineraryProjection> searchOrderedByComments(String country, String city, Pageable pageable);

    @Query("select distinct i from Itinerary i left join i.activities a where i.status='PUBLISHED' and LOWER(a.landmark.country) like LOWER(:country) and LOWER(a.landmark.city) like LOWER(:city) order by i.avgRating desc")
    Page<ItineraryProjection> searchOrderedByRating(String country, String city, Pageable pageable);

    @Query("select distinct i from Itinerary i left join i.activities a where i.status='PUBLISHED' and LOWER(a.landmark.country) like LOWER(:country) and LOWER(a.landmark.city) like LOWER(:city) AND :date <= i.createDate order by i.countComments desc")
    Page<ItineraryProjection> searchOrderedByCommentsLastMonth(String country, String city, LocalDateTime date, Pageable pageable);

    @Query("select distinct i from Itinerary i left join i.activities a where i.status='PUBLISHED' and LOWER(a.landmark.country) like LOWER(:country) and LOWER(a.landmark.city) like LOWER(:city) AND :date <= i.createDate order by i.avgRating desc")
    Page<ItineraryProjection> searchOrderedByRatingLastMonth(String country, String city, LocalDateTime date, Pageable pageable);


}