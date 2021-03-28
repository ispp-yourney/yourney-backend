package com.yourney.service;

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

    public Optional<Itinerary> findById(final Long id) {
        return itineraryRepository.findById(id);
    }

    public Iterable<Itinerary> findAll() {
        return itineraryRepository.findAll();
    }

    public Page<ItineraryProjection> searchByProperties(String country, String city, Double maxBudget,
            Pageable pageable) {
        return itineraryRepository.searchByProperties(country, city, maxBudget, pageable);
    }

    public Page<ItineraryProjection> searchByDistance(Double latitude, Double longitude, Pageable pageable) {
        return itineraryRepository.searchByDistance(latitude, longitude, pageable);
    }

    public Page<ItineraryProjection> searchByName(Pageable pageable, String cadena) {
        return itineraryRepository.searchByName(pageable, cadena);
    }

    public Page<ItineraryProjection> searchByUserId(Pageable pageable, Long userId) {
        return itineraryRepository.searchByUserId(pageable, userId);
    }

    public Page<ItineraryProjection> searchByUsername(Pageable pageable, String username) {
        return itineraryRepository.searchByUsername(pageable, username);
    }

    public Itinerary save(Itinerary itinerary) {
        Itinerary newItinerary = null;
        try {
            newItinerary = itineraryRepository.save(itinerary);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newItinerary;
    }

    public void deleteById(long id) {
        try {
            itineraryRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean existsById(long id) {
        return itineraryRepository.existsById(id);
    }

}