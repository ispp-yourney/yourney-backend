
package com.yourney.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourney.model.Activity;
import com.yourney.model.Landmark;
import com.yourney.model.projection.LandmarkProjection;
import com.yourney.repository.LandmarkRepository;

@Service
public class LandmarkService {

    @Autowired
    private LandmarkRepository landmarkRepository;

    public List<Landmark> findAllLandmarks() {
        return (List<Landmark>) landmarkRepository.findAll();
    }

    public Optional<Landmark> findById(Long id) {
        return landmarkRepository.findById(id);
    }

    public Landmark save(Landmark landmark) {
        Landmark newLandmark = landmarkRepository.save(landmark);
        return newLandmark;
    }

    public Optional<Activity> findOneActivityByLandmark(Long id) {
        return landmarkRepository.findOneActivityByLandmark(id);
    }

    public Optional<LandmarkProjection> findOneLandmarkProjection(Long id) {
        return landmarkRepository.findOneLandmarkProjection(id);
    }

    public List<String> findAllCountries() {
        return (List<String>) landmarkRepository.findAllCountries();
    }

    public List<String> findCitiesByCountry(String name) {
        return (List<String>) landmarkRepository.findCitiesByCountry(name);
    }

    public List<String> findAllCities() {
        return (List<String>) landmarkRepository.findAllCities();
    }

    public boolean existsById(long id) {
        return landmarkRepository.existsById(id);
    }

}
