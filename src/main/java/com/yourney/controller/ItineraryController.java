package com.yourney.controller;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yourney.model.Itinerary;
import com.yourney.model.StatusType;
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

	@GetMapping("/listexample")
	public ResponseEntity<Iterable<ItineraryProjection>> getListItinerariesExample() { 
		Iterable<ItineraryProjection> itinerariesList = itineraryService.findAllItineraryProjections();
		return new ResponseEntity<>(itinerariesList, HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<Page<Itinerary>> getListPublishedItineraries(
				@RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "10") int size,
				@RequestParam(defaultValue = "views") String order,
				@RequestParam(defaultValue = "true") boolean asc) { 

		Sort sort = Sort.by(order);
		if(!asc){
			sort = sort.descending();
		}

		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Itinerary> itineraries = itineraryService.findPublishedItineraryPages(pageable);

		return new ResponseEntity<Page<Itinerary>>(itineraries, HttpStatus.OK);
	}

	
	@GetMapping("/show/{id}")
	public ResponseEntity<?> showItinerary(@PathVariable("id") long id) {	
		if(itineraryService.existsById(id)){

			Itinerary foundItinerary = itineraryService.findById(id).get();

			if(foundItinerary.getStatus().equals(StatusType.DELETED)){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("El itinerario indicado, ha sido previamente eliminado."));
			} else if(foundItinerary.getStatus().equals(StatusType.DRAFT)&& !foundItinerary.getAuthor().getUsername().equals(userService.getCurrentUsername())){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("El itinerario solicitado no ha sido publicado por su autor."));
			}else {
				ItineraryProjection foundItineraryProjection = itineraryService.findOneItineraryProjection(id).orElse(null);
				return ResponseEntity.ok(foundItineraryProjection);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el itinerario indicado"));
		}
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
        } else {
			Itinerary foundItinerary = itineraryService.findById(id).get();

			if(foundItinerary.getStatus().equals(StatusType.DELETED)){
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("No existe el itinerario indicado"));
			}else if(foundItinerary.getStatus().equals(StatusType.DRAFT)){
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("No puede eliminar un itinerario no publicado"));
			}else if(!foundItinerary.getAuthor().getUsername().equals(userService.getCurrentUsername())){
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("No puede borrar un itinerario que no es suyo"));
			} else {
			foundItinerary.setStatus(StatusType.DELETED);
			itineraryService.save(foundItinerary);
		}	}
		
        return ResponseEntity.ok(new Message("Itinerario eliminado correctamente"));
    }

}