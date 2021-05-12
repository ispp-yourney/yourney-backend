package com.yourney.repository;

import java.util.List;

import com.yourney.model.LandmarkVisit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LandmarkVisitRepository extends CrudRepository<LandmarkVisit, Long> {

@Query("select case when count(lv)> 0 then true else false end from LandmarkVisit lv where lv.landmark.id = :landmarkId and lv.ip = :direccionIp")
Boolean comprobarExistenciaIpEnLandmark(@Param("landmarkId") Long landmarkId, @Param("direccionIp") String direccionIp);

@Query("select v from LandmarkVisit v where v.landmark.id = :landmarkIdentifier")
List<LandmarkVisit> findAllVisitsByLandmarkId(@Param("landmarkIdentifier") Long landmarkId);
}
