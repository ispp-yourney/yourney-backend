package com.yourney.repository;

import com.yourney.model.ItineraryVisit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryVisitRepository extends CrudRepository<ItineraryVisit, Long> {

@Query("select case when count(lv)> 0 then true else false end from ItineraryVisit lv where lv.itinerary.id = :itineraryId and lv.ip = :direccionIp")
Boolean comprobarExistenciaIpEnItinerario(@Param("itineraryId") Long itineraryId, @Param("direccionIp") String direccionIp);

}
