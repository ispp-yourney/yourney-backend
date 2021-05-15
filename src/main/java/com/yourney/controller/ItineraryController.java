package com.yourney.controller;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yourney.model.Image;
import com.yourney.model.Itinerary;
import com.yourney.model.ItineraryVisit;
import com.yourney.model.LandmarkVisit;
import com.yourney.model.StatusType;
import com.yourney.model.dto.ItineraryDto;
import com.yourney.model.dto.Message;
import com.yourney.model.projection.ItineraryProjection;
import com.yourney.security.model.RoleType;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;
import com.yourney.service.ActivityService;
import com.yourney.service.ImageService;
import com.yourney.service.ItineraryService;
import com.yourney.service.ItineraryVisitService;
import com.yourney.service.LandmarkVisitService;
import com.yourney.utils.ValidationUtils;

@RestController
@RequestMapping("/itinerary")
@CrossOrigin
public class ItineraryController {

	@Autowired
	private UserService userService;

	@Autowired
	private ItineraryService itineraryService;

    @Autowired
    private LandmarkVisitService landmarkVisitService;

    @Autowired
    private ItineraryVisitService itineraryVisitService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private ImageService imageService;


    private static final String ERROR_ITINERARIO_NO_EXISTE_STRING = "No existe el itinerario indicado";

	@GetMapping("/show/{id}")
	public ResponseEntity<?> showItinerary(@PathVariable("id") long id, HttpServletRequest request) {
		Optional<Itinerary> foundItinerary = itineraryService.findById(id);
		String currentUsername = userService.getCurrentUsername();

		if (!foundItinerary.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(ERROR_ITINERARIO_NO_EXISTE_STRING));
		}

		Itinerary itinerary = foundItinerary.get();

		if (itinerary.getStatus().equals(StatusType.DRAFT)
				&& !itinerary.getAuthor().getUsername().equals(currentUsername)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El itinerario solicitado no ha sido publicado por su autor."));
		} else if (!itinerary.getAuthor().getUsername().equals(currentUsername)) {
			
			if(!itineraryVisitService.existsByLandmarkIdAndIp(itinerary.getId(), request.getRemoteAddr())){
				ItineraryVisit iv = new ItineraryVisit();
				iv.setIp(request.getRemoteAddr());
				iv.setItinerary(itinerary);
				itineraryVisitService.save(iv);
			}

			itinerary.getActivities().stream().map(a->a.getLandmark()).forEach(l-> {
				if(!landmarkVisitService.existsByLandmarkIdAndIp(l.getId(), request.getRemoteAddr())){
					LandmarkVisit lv = new LandmarkVisit();
					lv.setIp(request.getRemoteAddr());
					lv.setLandmark(l);
					landmarkVisitService.save(lv);
				}}
			);
			itineraryService.save(itinerary);
		}

		return ResponseEntity.ok(foundItinerary.get());
	}

	@GetMapping("/search")
	public ResponseEntity<Page<ItineraryProjection>> searchByProperties(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String country,
			@RequestParam(defaultValue = "") String city, @RequestParam(defaultValue = "-1") double maxBudget, 
			@RequestParam(defaultValue = "-1") int maxDays) {

		if (maxBudget == -1) {
			maxBudget = 1000000000.;
		}
		if (maxDays == -1) {
			maxDays = 1000000000;
		}

		Pageable pageable = PageRequest.of(page, size);
		Page<ItineraryProjection> itineraries = itineraryService.searchByProperties("%" + country + "%",
				"%" + city + "%", maxBudget, maxDays, pageable);

		return new ResponseEntity<Page<ItineraryProjection>>(itineraries, HttpStatus.OK);
	}

	@GetMapping("/searchByDistance")
	public ResponseEntity<Page<ItineraryProjection>> searchItinerariesByDistance(
			@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<ItineraryProjection> itineraries = itineraryService.searchByDistance(latitude, longitude, pageable);

		return new ResponseEntity<Page<ItineraryProjection>>(itineraries, HttpStatus.OK);
	}

	@GetMapping("/searchByName")
	public ResponseEntity<Page<ItineraryProjection>> searchItinerariesByName(
			@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Page<ItineraryProjection> itinerariesListOrdered = itineraryService.searchByName(PageRequest.of(page, size),
				"%" + name + "%");
		return new ResponseEntity<Page<ItineraryProjection>>(itinerariesListOrdered, HttpStatus.OK);
	}

	@GetMapping("/searchByUserId")
	public ResponseEntity<Page<ItineraryProjection>> listItinerariesByUser(
			@RequestParam(defaultValue = "0") long userId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Page<ItineraryProjection> itinerariesListOrdered = itineraryService.searchByUserId(PageRequest.of(page, size),
				userId);
		return new ResponseEntity<Page<ItineraryProjection>>(itinerariesListOrdered, HttpStatus.OK);
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<Page<ItineraryProjection>> listItinerariesByUser(@PathVariable("username") String username,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		String currentUsername = userService.getCurrentUsername();
		if (!currentUsername.equals(username)) {
			Page<ItineraryProjection> itinerariesListOrdered = itineraryService.searchByUsername(PageRequest.of(page, size),
					username);
			return new ResponseEntity<Page<ItineraryProjection>>(itinerariesListOrdered, HttpStatus.OK);
		} else {
			Page<ItineraryProjection> userItinerariesListOrdered = itineraryService.searchByCurrentUsername(PageRequest.of(page, size),
					username);
			return new ResponseEntity<Page<ItineraryProjection>>(userItinerariesListOrdered, HttpStatus.OK);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<?> createItinerary(@Valid @RequestBody ItineraryDto itineraryDto, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
		}

		String username = userService.getCurrentUsername();

		if (username.equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario no tiene permiso de creación sin registrarse."));
		}

		Optional<User> usuario = userService.getByUsername(username);
		if (!usuario.isPresent()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario debe estar registrado para publicar un itinerario."));
		}

		Itinerary newItinerary = new Itinerary();
		BeanUtils.copyProperties(itineraryDto, newItinerary, "id", "status", "createDate", "activities", "author",
				"views", "image");

		Optional<Image> defaultImage = imageService.findById(78);
		if (!defaultImage.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("La imagen seleccionada no ha sido encontrada."));
		}

		newItinerary.setStatus(StatusType.PUBLISHED);
		newItinerary.setCreateDate(LocalDateTime.now());
		newItinerary.setActivities(new ArrayList<>());
		newItinerary.setAuthor(usuario.get());
		newItinerary.setImage(defaultImage.get());

		Itinerary createdItinerary = itineraryService.save(newItinerary);

		if (createdItinerary == null) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
					.body(new Message("Ha ocurrido un error a la hora de actualizar este itinerario."));
		} else {
			return ResponseEntity.ok(createdItinerary);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateItinerary(@Valid @RequestBody ItineraryDto itineraryDto, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
		}

		String username = userService.getCurrentUsername();

		if (username.equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario no tiene permiso de modificación sin registrarse."));
		}

		Optional<Itinerary> itineraryToUpdate = itineraryService.findById(itineraryDto.getId());

		if (!itineraryToUpdate.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(ERROR_ITINERARIO_NO_EXISTE_STRING));
		}

		Itinerary itinerary = itineraryToUpdate.get();
		Optional<User> foundUser = userService.getByUsername(userService.getCurrentUsername());

		if (!itinerary.getAuthor().getUsername().equals(username) && !(foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN)))){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
					new Message("El usuario no tiene permiso de modificación de este itinerario, que no es suyo."));
		}
		BeanUtils.copyProperties(itineraryDto, itinerary, "id", "createDate", "activities", "author", "views", "image");

		Itinerary updatedItinerary = itineraryService.save(itinerary);

		if (updatedItinerary == null) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
					.body(new Message("Ha ocurrido un error a la hora de actualizar este itinerario."));
		} else {
			return ResponseEntity.ok(updatedItinerary);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteItinerary(@PathVariable("id") long id) {
		Optional<Itinerary> foundItinerary = itineraryService.findById(id);

		if (!foundItinerary.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(ERROR_ITINERARIO_NO_EXISTE_STRING));
		} else {
			Itinerary itinerary = foundItinerary.get();
			Optional<User> foundUser = userService.getByUsername(userService.getCurrentUsername());

			if (!itinerary.getAuthor().getUsername().equals(userService.getCurrentUsername()) && !(foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN)))) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new Message("No puede borrar un itinerario que no es suyo"));
			} else {
				if (itinerary.getViews()>0) {
					List<ItineraryVisit> visitsItineraryToDelete = itineraryVisitService.findAllVisitsByItinerary(itinerary);
					visitsItineraryToDelete.stream().forEach(v-> itineraryVisitService.delete(v));
				}
				itinerary.getActivities().stream().forEach(a -> activityService.deleteById(a.getId()));
				itineraryService.deleteById(id);
			}
		}
		return ResponseEntity.ok(new Message("Itinerario eliminado correctamente"));
	}

	@GetMapping("/searchOrderedByComments")
	public ResponseEntity<?> searchOrderedByComments(@RequestParam(defaultValue = "0") int page, 
	@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String country,
	@RequestParam(defaultValue = "") String city) {

		Optional<User> foundUser = userService.getByUsername(userService.getCurrentUsername());

		if (foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN))) {
			Pageable pageable = PageRequest.of(page, size);
			Page<ItineraryProjection> itinerariesListOrdered = itineraryService.searchOrderedByComments("%" + country + "%", "%" + city + "%", pageable);

			return new ResponseEntity<Page<ItineraryProjection>>(itinerariesListOrdered, HttpStatus.OK);
			
		} else {

			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario no tiene permiso para ver esta consulta"));
		}
		
	}

	@GetMapping("/searchOrderedByRating")
	public ResponseEntity<?> searchOrderedByRating(@RequestParam(defaultValue = "0") int page, 
	@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String country,
	@RequestParam(defaultValue = "") String city) {

		Optional<User> foundUser = userService.getByUsername(userService.getCurrentUsername());

		if (foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN))) {
			Pageable pageable = PageRequest.of(page, size);
			Page<ItineraryProjection> itinerariesListOrdered = itineraryService.searchOrderedByRating("%" + country + "%", "%" + city + "%", pageable);

			return new ResponseEntity<Page<ItineraryProjection>>(itinerariesListOrdered, HttpStatus.OK);
			
		} else {

			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario no tiene permiso para ver esta consulta"));
		}
		
	}

	@GetMapping("/searchOrderedByViews")
	public ResponseEntity<?> searchOrderedByViews(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String country,
			@RequestParam(defaultValue = "") String city) {
		
		Optional<User> foundUser = userService.getByUsername(userService.getCurrentUsername());

		if (foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN))) {
			Pageable pageable = PageRequest.of(page, size);
			Page<ItineraryProjection> itineraries = itineraryService.searchOrderedByViews("%" + country + "%", "%" + city + "%", pageable);

			return new ResponseEntity<Page<ItineraryProjection>>(itineraries, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario no tiene permiso para realizar esta consulta."));
		}

	}
	

	@GetMapping("/searchOrderedByComments/lastMonth")
	public ResponseEntity<?> searchOrderedByCommentsLastMonth(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String country,
			@RequestParam(defaultValue = "") String city) {

		Optional<User> foundUser =  userService.getByUsername(userService.getCurrentUsername());
		
		if (foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN))) {
			Pageable pageable = PageRequest.of(page, size);
			Page<ItineraryProjection> itineraries = itineraryService.searchOrderedByCommentsLastMonth("%" + country + "%", "%" + city + "%", pageable);
			
			return new ResponseEntity<Page<ItineraryProjection>>(itineraries, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario no tiene permiso para ver esta consulta"));
		}

	}

	@GetMapping("/searchOrderedByRating/lastMonth")
	public ResponseEntity<?> searchOrderedByRatingLastMonth(@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String country,
		@RequestParam(defaultValue = "") String city) {
		
		Optional<User> foundUser =  userService.getByUsername(userService.getCurrentUsername());
		
		if (foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN))) {
			Pageable pageable = PageRequest.of(page, size);
			Page<ItineraryProjection> itineraries = itineraryService.searchOrderedByRatingLastMonth("%" + country + "%", "%" + city + "%", pageable);

			return new ResponseEntity<Page<ItineraryProjection>>(itineraries, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario no tiene permiso para ver esta consulta"));
		}
	}


}