
package com.yourney.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yourney.model.Itinerary;
import com.yourney.model.projection.ItineraryProjection;
import com.yourney.repository.ItineraryRepository;

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

    public Page<Itinerary> findPublishedItineraryPages(Pageable pageable) {
        return itineraryRepository.findAll(pageable);
    }
    
	public Optional<Itinerary> findById(final Long id) {
		return itineraryRepository.findById(id);
	}

	public Optional<ItineraryProjection> findOneItineraryProjection(final Long id) {
		return itineraryRepository.findOneItineraryProjection(id);
	}    

    public void save(Itinerary itinerary) {
        itineraryRepository.save(itinerary);
    }

    public void deleteById(long id) {
		itineraryRepository.deleteById(id);
    }

    public boolean existsById(long id) {
		return itineraryRepository.existsById(id);
    }
}   