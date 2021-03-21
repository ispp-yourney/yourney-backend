package com.yourney.repository;

import java.util.Optional;

import com.yourney.model.Season;
import com.yourney.model.projection.SeasonProjection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SeasonRepository extends CrudRepository<Season, Long> {

    @Query("select it from Season it")
	Iterable<SeasonProjection> findAllSeasonProjections();

    @Query("select it from Season it where it.id=:id")
	Optional<SeasonProjection> findOneSeasonProjection(@Param("id") long idItinerario);

}