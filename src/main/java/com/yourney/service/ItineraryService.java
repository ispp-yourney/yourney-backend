
package com.yourney.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yourney.model.Itinerary;
import com.yourney.model.StatusType;
import com.yourney.model.projection.ItineraryDetailsProjection;
import com.yourney.model.projection.ItineraryProjection;
import com.yourney.repository.ItineraryRepository;

@Service
public class ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    // public List<Itinerary> findAll(){
    // return (List<Itinerary>) itineraryRepository.findAll();
    // }

    public List<Itinerary> findAllItinerary() {
        return (List<Itinerary>) itineraryRepository.findAllItinerary();
    }

    public List<ItineraryProjection> findAllItineraryProjections() {
        return (List<ItineraryProjection>) itineraryRepository.findAllItineraryProjections();
    }

    public Page<ItineraryProjection> findAllItineraryProjectionsOrdered(Pageable pageable) {
        return itineraryRepository.findAllItineraryProjectionsOrdered(pageable);
    }

    public Page<ItineraryProjection> findPublishedItineraryPages(Pageable pageable) {
        return itineraryRepository.findByStatus(StatusType.PUBLISHED, pageable);
    }

    public Page<ItineraryProjection> findPublishedItineraryPagesByCountry(String country, Pageable pageable) {
        return itineraryRepository.findByActivitiesLandmarkCountry(country, pageable);
    }

    public Page<ItineraryProjection> findPublishedItineraryPagesByCity(String city, Pageable pageable) {
        return itineraryRepository.findByActivitiesLandmarkCity(city, pageable);
    }

    public Page<ItineraryProjection> findPublishedItineraryPagesByDistance(Double latitude, Double longitude,
            Pageable pageable) {
        return itineraryRepository.findByActivitiesLandmarkDistance(latitude, longitude, pageable);
    }

    public Optional<Itinerary> findById(final Long id) {
        return itineraryRepository.findById(id);
    }

    public Optional<ItineraryProjection> findOneItineraryProjection(final Long id) {
        return itineraryRepository.findOneItineraryProjection(id);
    }

    public Optional<ItineraryDetailsProjection> findOneItineraryDetailsProjection(final Long id) {
        return itineraryRepository.findOneItineraryDetailsProjection(id);
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

    public Page<ItineraryProjection> findSearchItineraryProjectionsOrdered(Pageable pageable, String cadena) {
        return itineraryRepository.findSearchItineraryProjectionsOrdered(pageable, cadena);
    }

    public List<Itinerary> findSearchItinerary(String cadena) {
        return (List<Itinerary>) itineraryRepository.findSearchItinerary(cadena);
    }

    public Page<ItineraryProjection> findUserItineraryProjectionsOrdered(Pageable pageable, Long userId) {
        return itineraryRepository.findUserItineraryProjectionsOrdered(pageable, userId);
    }

    public List<Itinerary> findUserItinerary(Long userId) {
        return (List<Itinerary>) itineraryRepository.findUserItinerary(userId);
    }

}