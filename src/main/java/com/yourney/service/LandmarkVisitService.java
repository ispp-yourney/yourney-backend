package com.yourney.service;

import java.util.Optional;

import com.yourney.model.LandmarkVisit;
import com.yourney.repository.LandmarkVisitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandmarkVisitService {

    @Autowired
    private LandmarkVisitRepository landmarkVisitRepository;

    public Optional<LandmarkVisit> findById(long id) {
        return landmarkVisitRepository.findById(id);
    }

    public boolean existsByLandmarkIdAndIp(long id, String ip) {
        return landmarkVisitRepository.comprobarExistenciaIpEnLandmark(id, ip);
    }

    public LandmarkVisit save(LandmarkVisit lvisit) {
        LandmarkVisit newLvisit = null;
        try {
            newLvisit = landmarkVisitRepository.save(lvisit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newLvisit;
    }
}
