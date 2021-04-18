
package com.yourney.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.yourney.model.Landmark;
import com.yourney.model.projection.LandmarkProjection;
import com.yourney.repository.LandmarkRepository;

@Service
public class LandmarkService {

    @Autowired
    private LandmarkRepository landmarkRepository;

    public Page<LandmarkProjection> searchByProperties(String country, String city, String name, Integer size, Pageable pageable) {
        return landmarkRepository.searchByProperties(country, city, name, pageable);
    }

    public Iterable<Landmark> findAll() {
        return landmarkRepository.findAll();
    }

    public Optional<Landmark> findById(Long id) {
        return landmarkRepository.findById(id);
    }

    public Boolean existsActivityByLandmarkId(Long id) {
        return landmarkRepository.existsActivityByLandmarkId(id);
    }

    public Iterable<String> findAllCountries(boolean itinerary) {
        return landmarkRepository.findAllCountries(itinerary);
    }

    public Iterable<String> findCitiesByCountry(String name) {
        return landmarkRepository.findCitiesByCountry(name);
    }

    public Iterable<String> findAllCities(boolean itinerary) {
        return landmarkRepository.findAllCities(itinerary);
    }

    public Landmark save(Landmark landmark) {
        Landmark newLandmark = null;

        try {
            newLandmark = landmarkRepository.save(landmark);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newLandmark;
    }

    public void deleteById(Long id) {
        landmarkRepository.deleteById(id);
    }
}
