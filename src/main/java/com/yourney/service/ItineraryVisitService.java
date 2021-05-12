package com.yourney.service;

import java.util.List;
import java.util.Optional;

import com.yourney.model.Itinerary;
import com.yourney.model.ItineraryVisit;
import com.yourney.repository.ItineraryVisitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItineraryVisitService {

    @Autowired
    private ItineraryVisitRepository itineraryVisitRepository;

    public Optional<ItineraryVisit> findById(long id) {
        return itineraryVisitRepository.findById(id);
    }

    public boolean existsByLandmarkIdAndIp(long id, String ip) {
        return itineraryVisitRepository.comprobarExistenciaIpEnItinerario(id, ip);
    }

    public ItineraryVisit save(ItineraryVisit lvisit) {
        ItineraryVisit newLvisit = null;
        try {
            newLvisit = itineraryVisitRepository.save(lvisit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newLvisit;
    }

    public void delete(ItineraryVisit ivisit) {
        try {
            itineraryVisitRepository.delete(ivisit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ItineraryVisit> findAllVisitsByItinerary(Itinerary itinerary) {
        Long identificadorItinerary = itinerary.getId();
        return itineraryVisitRepository.findAllVisitsByItineraryId(identificadorItinerary);
    }
}
