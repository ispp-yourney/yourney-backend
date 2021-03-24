package com.yourney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourney.service.LandmarkService;

@RestController
@RequestMapping("/landmark")
@CrossOrigin
public class LandmarkController {
    
    @Autowired
    private LandmarkService landmarkService;

    @GetMapping("/country/list")
    public ResponseEntity<Iterable<String>> listCountries() {
        return ResponseEntity.ok(landmarkService.findAllCountries());
    }

    @GetMapping("/country/{name}/city/list")
    public ResponseEntity<Iterable<String>> listCitiesByCountry(@PathVariable("name") String name) {
        return ResponseEntity.ok(landmarkService.findCitiesByCountry(name));
    }
    @GetMapping("/city/list")
    public ResponseEntity<Iterable<String>> listCities() {
        return ResponseEntity.ok(landmarkService.findAllCities());
    }

 
}
