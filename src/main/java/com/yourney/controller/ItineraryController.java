package com.yourney.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import com.yourney.model.Activity;
import com.yourney.model.Itinerary;
import com.yourney.model.dto.ItineraryDto;
import com.yourney.model.dto.Message;
import com.yourney.model.projection.ItineraryProjection;
import com.yourney.security.service.UserService;
import com.yourney.service.ItineraryService;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {

	@Autowired
	private UserService userService;

	@Autowired
	private ItineraryService itineraryService;

	@GetMapping("/list")
	public ResponseEntity<Iterable<ItineraryProjection>> getListItineraries() { 
		Iterable<ItineraryProjection> itinerariesList = itineraryService.findAllItineraryProjections();
		return new ResponseEntity<>(itinerariesList, HttpStatus.OK);
	}

	
	@GetMapping("/show/{id}")
	public ResponseEntity<Itinerary> showItinerary(@PathVariable("id") long id) {
		return ResponseEntity.ok(itineraryService.findById(id).orElse(null));
	}
	
	@PostMapping("/create")
	public ResponseEntity<Itinerary> createItinerary(@RequestBody ItineraryDto itineraryDto) {
		Itinerary newItinerary = new Itinerary();
		BeanUtils.copyProperties(itineraryDto, newItinerary, "id", "createDate", "updateDate", "deleteDate");

		// Muestra el usuario actual por consola
		System.out.println(userService.getCurrentUsername());

		newItinerary.setCreateDate(LocalDateTime.now());
		itineraryService.save(newItinerary);
		return ResponseEntity.ok(newItinerary);
	}	
	
	@PutMapping("/update")
    public ResponseEntity<?> updateItinerary(@RequestBody Itinerary itineraryDto) {
        if (!itineraryService.existsById(itineraryDto.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el itinerario indicado"));
        }

        Itinerary itineraryToUpdate = itineraryService.findById(itineraryDto.getId()).orElse(null);
        BeanUtils.copyProperties(itineraryDto, itineraryToUpdate, "id", "createDate", "updateDate", "deleteDate");

		itineraryToUpdate.setUpdateDate(LocalDateTime.now());
        itineraryService.save(itineraryToUpdate);
        return ResponseEntity.ok(itineraryToUpdate);
    }	

	@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItinerary(@PathVariable("id") long id) {
        if (!itineraryService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No exites el itinerario indicado"));
        }

        itineraryService.deleteById(id);
        return ResponseEntity.ok(new Message("Itinerario eliminado correctamente"));
    }

}