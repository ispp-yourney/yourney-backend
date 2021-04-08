package com.yourney.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yourney.model.Landmark;
import com.yourney.model.projection.LandmarkProjection;

@Repository
public interface LandmarkRepository extends CrudRepository<Landmark, Long> {

    @Query("select distinct l.country from Landmark l order by country")
    Iterable<String> findAllCountries();

    @Query("select distinct l.city from Landmark l where l.country=:name order by city")
    Iterable<String> findCitiesByCountry(@Param("name") String name);

    @Query("select distinct l.city from Landmark l order by city")
    Iterable<String> findAllCities();

    @Query("select case when count(ac)> 0 then true else false end from Activity ac where ac.landmark.id=:id")
    Boolean existsActivityByLandmarkId(Long id);

    @Query("select l.id as id, l.name as name, l.description as description, l.price as price, l.country as country, l.city as city, l.latitude as latitude, l.longitude as longitude, l.endPromotionDate as endPromotionDate, l.category as category, l.email as email, l.phone as phone, l.website as website, l.instagram as instagram, l.twitter as twitter, l.createDate as createDate, l.views as views, l.image as image, l.imageUrl as imageUrl, l.endPromotionDate >= CURRENT_DATE as p from Landmark l where l.country like :country and l.city like :city and l.name like :name order by p, l.views desc")
    Page<LandmarkProjection> searchByProperties(String country, String city, String name, Pageable pageable);
}