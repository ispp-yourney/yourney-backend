
package com.yourney.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourney.repository.LandmarkRepository;

@Service
public class LandmarkService {
    
    @Autowired
    private LandmarkRepository landmarkRepository;

    public List<String> findAllCountries() {
        return (List<String>) landmarkRepository.findAllCountries();
    }
    public List<String> findCitiesByCountry(String name) {
    	return (List<String>) landmarkRepository.findCitiesByCountry(name);
    }
    public List<String> findAllCities() {
        return (List<String>) landmarkRepository.findAllCities();
    }

}
