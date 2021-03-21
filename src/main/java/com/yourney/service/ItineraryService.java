
package com.yourney.service;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.NotFoundException;

import com.yourney.model.Itinerary;
import com.yourney.model.projection.ItineraryProjection;
import com.yourney.repository.ItineraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItineraryService {
    
    @Autowired
    private ItineraryRepository itineraryRepository;

    public List<Itinerary> findAll(){
        return (List<Itinerary>) itineraryRepository.findAll();
    }

    public List<ItineraryProjection> findAllItineraryProjections() {
        return (List<ItineraryProjection>) itineraryRepository.findAllItineraryProjections();
    }

	public Optional<Itinerary> findById(final Long id) {
		return itineraryRepository.findById(id);
	}

	public Optional<ItineraryProjection> findOneItineraryProjection(final Long id) {
		return itineraryRepository.findOneItineraryProjection(id);
	}    

    public Itinerary save(Itinerary itinerary) {
        Itinerary newItinerary = itineraryRepository.save(itinerary);
        return newItinerary;
    }

    public void deleteById(long id) {
		itineraryRepository.deleteById(id);
    }

    public boolean existsById(long id) {
		return itineraryRepository.existsById(id);
    }
}   