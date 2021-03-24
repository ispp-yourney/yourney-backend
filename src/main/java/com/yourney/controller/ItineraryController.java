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

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import com.yourney.model.Image;

import com.yourney.model.Itinerary;
import com.yourney.model.StatusType;
import com.yourney.model.dto.ItineraryDto;
import com.yourney.model.dto.Message;
import com.yourney.model.dto.Search;
import com.yourney.model.projection.ItineraryDetailsProjection;
import com.yourney.model.projection.ItineraryProjection;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;
import com.yourney.service.ImageService;
import com.yourney.service.ItineraryService;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {

	@Autowired
	private UserService userService;

	@Autowired
	private ItineraryService itineraryService;

	@Autowired
	private ImageService imageService;

	@GetMapping("/list_all")
	public ResponseEntity<Iterable<ItineraryProjection>> getListItineraries() {
		Iterable<ItineraryProjection> itinerariesList = itineraryService.findAllItineraryProjections();
		return new ResponseEntity<>(itinerariesList, HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<Page<Itinerary>> getListPublishedItineraries(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "views") String order,
			@RequestParam(defaultValue = "true") boolean asc) {

		Sort sort = Sort.by(order);
		if (!asc) {
			sort = sort.descending();
		}

		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Itinerary> itineraries = itineraryService.findPublishedItineraryPages(pageable);

		return new ResponseEntity<Page<Itinerary>>(itineraries, HttpStatus.OK);
	}

	@GetMapping("/listByCountry/{name}")
	public ResponseEntity<Page<Itinerary>> getListPublishedItinerariesByCountries(
			@PathVariable("name") String countryName, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "views") String order,
			@RequestParam(defaultValue = "true") boolean asc) {

		Sort sort = Sort.by(order);
		if (!asc) {
			sort = sort.descending();
		}

		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Itinerary> itineraries = itineraryService.findPublishedItineraryPagesByCountry(countryName, pageable);

		return new ResponseEntity<Page<Itinerary>>(itineraries, HttpStatus.OK);
	}

	@GetMapping("/list/{page}")
	public ResponseEntity<Iterable<ItineraryProjection>> getListItineraries(@PathVariable("page") int page) {
		Iterable<Itinerary> itinerariesList = itineraryService.findAllItinerary();
		itinerariesSetPoints(itinerariesList);
		List<ItineraryProjection> itinerariesListOrdered = itineraryService
				.findAllItineraryProjectionsOrdered(PageRequest.of(page - 1, 10));
		return new ResponseEntity<>(itinerariesListOrdered, HttpStatus.OK);
	}

	@PostMapping("/search/{page}")
	public ResponseEntity<Iterable<ItineraryProjection>> getSearchItineraries(@PathVariable("page") int page,
			@RequestBody Search cadena) {
		Iterable<Itinerary> itinerariesList = itineraryService.findSearchItinerary(cadena.getCadena());
		itinerariesSetPoints(itinerariesList);
		List<ItineraryProjection> itinerariesListOrdered = itineraryService
				.findSearchItineraryProjectionsOrdered(PageRequest.of(page - 1, 10), cadena.getCadena());
		return new ResponseEntity<>(itinerariesListOrdered, HttpStatus.OK);
	}

	@PostMapping("/user/{page}")
	public ResponseEntity<Iterable<ItineraryProjection>> getUserItineraries(@PathVariable("page") int page,
			@RequestBody Search userId) {
		Iterable<Itinerary> itinerariesList = itineraryService.findUserItinerary(userId.getUserId());
		itinerariesSetPoints(itinerariesList);
		List<ItineraryProjection> itinerariesListOrdered = itineraryService
				.findUserItineraryProjectionsOrdered(PageRequest.of(page - 1, 10), userId.getUserId());
		return new ResponseEntity<>(itinerariesListOrdered, HttpStatus.OK);
	}

	@GetMapping("/show/{id}")
	public ResponseEntity<?> showItinerary(@PathVariable("id") long id) {
		if (itineraryService.existsById(id)) {

			Itinerary foundItinerary = itineraryService.findById(id).get();

			if (foundItinerary.getStatus().equals(StatusType.DELETED)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new Message("El itinerario indicado, ha sido previamente eliminado."));
			} else if (foundItinerary.getStatus().equals(StatusType.DRAFT)
					&& !foundItinerary.getAuthor().getUsername().equals(userService.getCurrentUsername())) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new Message("El itinerario solicitado no ha sido publicado por su autor."));
			} else {
				foundItinerary.setViews(foundItinerary.getViews() + 1);
				itineraryService.save(foundItinerary);
				ItineraryDetailsProjection foundItineraryProjection = itineraryService
						.findOneItineraryDetailsProjection(id).orElse(null);
				return ResponseEntity.ok(foundItineraryProjection);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el itinerario indicado"));
		}
	}

	@PostMapping("/create")
	public ResponseEntity<?> createItinerary(@RequestBody ItineraryDto itineraryDto) {
		String username = userService.getCurrentUsername();

		if (username.equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario no tiene permiso de creación sin registrarse."));
		}

		Optional<User> usuario = userService.getByUsername(username);

		Itinerary newItinerary = new Itinerary();
		BeanUtils.copyProperties(itineraryDto, newItinerary, "id", "views", "createDate", "updateDate", "deleteDate");

		newItinerary.setCreateDate(LocalDateTime.now());
		newItinerary.setActivities(new ArrayList<>());
		newItinerary.setAuthor(usuario.get());
		newItinerary.setViews(0);

		if (itineraryDto.getImage() != null) {
			Image imagen = imageService.findByURL(itineraryDto.getImage()).orElse(null);
			if (imagen != null) {
				newItinerary.setImage(imagen);
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
						new Message("El usuario debe de subir una imagen principal antes de crear el itinerario."));
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario debe de subir una imagen principal antes de crear el itinerario."));
		}

		Itinerary createdItinerary = itineraryService.save(newItinerary);

		return ResponseEntity.ok(itineraryService.findOneItineraryProjection(createdItinerary.getId()));
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateItinerary(@RequestBody ItineraryDto itineraryDto) {

		String username = userService.getCurrentUsername();

		if (username.equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario no tiene permiso de modficación sin registrarse."));
		} else if (!itineraryService.existsById(itineraryDto.getId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el itinerario indicado"));
		}

		Itinerary itineraryToUpdate = itineraryService.findById(itineraryDto.getId()).orElse(null);

		if (!itineraryToUpdate.getAuthor().getUsername().equals(username)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
					new Message("El usuario no tiene permiso de modficación de este itinerario, que no es suyo."));
		}
		BeanUtils.copyProperties(itineraryDto, itineraryToUpdate, "id", "views", "createDate", "updateDate",
				"deleteDate");

		itineraryToUpdate.setUpdateDate(LocalDateTime.now());

		if (itineraryDto.getImage() != null) {
			Image imagen = imageService.findByURL(itineraryDto.getImage()).orElse(null);
			if (imagen != null) {
				itineraryToUpdate.setImage(imagen);
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
						new Message("El usuario debe de subir una imagen principal antes de crear el itinerario."));
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario debe de subir una imagen principal antes de crear el itinerario."));
		}

		Itinerary updatedItinerary = itineraryService.save(itineraryToUpdate);

		return ResponseEntity.ok(itineraryService.findOneItineraryProjection(updatedItinerary.getId()));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteItinerary(@PathVariable("id") long id) {
		if (!itineraryService.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el itinerario indicado"));
		} else {
			Itinerary foundItinerary = itineraryService.findById(id).get();

			if (foundItinerary.getStatus().equals(StatusType.DELETED)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new Message("No existe el itinerario indicado"));
			} else if (foundItinerary.getStatus().equals(StatusType.DRAFT)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new Message("No puede eliminar un itinerario no publicado"));
			} else if (!foundItinerary.getAuthor().getUsername().equals(userService.getCurrentUsername())) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new Message("No puede borrar un itinerario que no es suyo"));
			} else {
				foundItinerary.setDeleteDate(LocalDateTime.now());
				foundItinerary.setStatus(StatusType.DELETED);
				itineraryService.save(foundItinerary);
			}
		}
		return ResponseEntity.ok(new Message("Itinerario eliminado correctamente"));
	}

	private void itinerariesSetPoints(Iterable<Itinerary> itinerariesList) {
		for (Itinerary it : itinerariesList) {
			it.setPoints();
			itineraryService.save(it);
		}
	}

}