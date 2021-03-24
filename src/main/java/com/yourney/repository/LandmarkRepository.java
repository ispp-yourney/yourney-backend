package com.yourney.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yourney.model.Landmark;

@Repository
public interface LandmarkRepository extends CrudRepository<Landmark, Long> {

    @Query("select distinct l.country from Landmark l order by country")
    Iterable<String> findAllCountries();

    @Query("select distinct l.city from Landmark l where l.country=:name order by city")
    Iterable<String> findCitiesByCountry(@Param("name") String name);

    @Query("select distinct l.city from Landmark l order by city")
    Iterable<String> findAllCities();

}