package com.yourney.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yourney.model.Activity;
import com.yourney.model.Image;
import com.yourney.model.Landmark;
import com.yourney.model.LandmarkVisit;
import com.yourney.model.dto.LandmarkDto;
import com.yourney.model.dto.Message;
import com.yourney.model.projection.LandmarkProjection;
import com.yourney.security.model.RoleType;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;
import com.yourney.service.ActivityService;
import com.yourney.service.ImageService;
import com.yourney.service.LandmarkService;
import com.yourney.service.LandmarkVisitService;
import com.yourney.utils.ValidationUtils;

@RestController
@RequestMapping("/landmark")
@CrossOrigin
public class LandmarkController {

	private static final String NOT_REGISTERED = "El usuario no tiene permiso de modificación sin registrarse.";
    @Autowired
    private LandmarkService landmarkService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LandmarkVisitService landmarkVisitService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    private static final String ANONYMOUS_USER_STRING = "anonymousUser";

    @GetMapping("/country/list")
    public ResponseEntity<Iterable<String>> listCountries(
    		@RequestParam(defaultValue = "false") boolean itinerary) {
        return ResponseEntity.ok(landmarkService.findAllCountries(itinerary));
    }

    @GetMapping("/country/{name}/city/list")
    public ResponseEntity<Iterable<String>> listCitiesByCountry(@PathVariable("name") String name) {
        return ResponseEntity.ok(landmarkService.findCitiesByCountry(name));
    }

    @GetMapping("/city/list")
    public ResponseEntity<Iterable<String>> listCities(
    		@RequestParam(defaultValue = "false") boolean itinerary) {
        return ResponseEntity.ok(landmarkService.findAllCities(itinerary));
    }

	@GetMapping("/search")
	public ResponseEntity<Iterable<LandmarkProjection>> searchByProperties(
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "") String country,
			@RequestParam(defaultValue = "") String city, @RequestParam(defaultValue = "") String name) {

		Iterable<LandmarkProjection> landmarks = landmarkService.searchByProperties("%" + country + "%",
				"%" + city + "%","%" + name + "%", PageRequest.of(page, size));

		return new ResponseEntity<Iterable<LandmarkProjection>>(landmarks, HttpStatus.OK);
	}

    @GetMapping("/hasActivity/{id}")
    public ResponseEntity<Boolean> hasActivity(@PathVariable("id") long id) {
		Boolean hasActivity = this.landmarkService.existsActivityByLandmarkId(id);

		return new ResponseEntity<Boolean>(hasActivity, HttpStatus.OK);
	}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLandMark(@PathVariable("id") long id) {

        Optional<Landmark> foundLandmark = landmarkService.findById(id);

        if (!foundLandmark.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El punto de interés que intenta eliminar no existe."));
        }

        String username = userService.getCurrentUsername();
        if (username.equals(ANONYMOUS_USER_STRING)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de eliminar sin registrarse."));
        }

        Landmark landmarkToDelete = foundLandmark.get();

        boolean existsActivityFromLandmark= landmarkService.existsActivityByLandmarkId(landmarkToDelete.getId());
        if (existsActivityFromLandmark) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El punto de interés se encuentra asociado con al menos una actividad."));
        }

        if (landmarkToDelete.getViews()>0) {
            List<LandmarkVisit> visitsLandmarkToDelete = landmarkVisitService.findAllVisitsByLandmark(landmarkToDelete);
            visitsLandmarkToDelete.stream().forEach(v-> landmarkVisitService.delete(v));
        }

        landmarkService.deleteById(landmarkToDelete.getId());

        return ResponseEntity.ok(new Message("El punto de interés ha sido eliminado correctamente."));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showLandMark(@PathVariable("id") long id, HttpServletRequest request) {

        Optional<Landmark> landmark = landmarkService.findById(id);

        if (landmark.isPresent()) {
            Landmark foundLandmark = landmark.get();
            
            if(!landmarkVisitService.existsByLandmarkIdAndIp(foundLandmark.getId(), request.getRemoteAddr())){
                LandmarkVisit lv = new LandmarkVisit();
                lv.setIp(request.getRemoteAddr());
                lv.setLandmark(foundLandmark);
                landmarkVisitService.save(lv);
            }

            return ResponseEntity.ok(foundLandmark);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el POI indicado."));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLandMark(@RequestBody @Valid LandmarkDto landmarkDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
        }

        String username = userService.getCurrentUsername();
        Optional<Landmark> foundLandmark = landmarkService.findById(landmarkDto.getId());

        if (username.equals(ANONYMOUS_USER_STRING)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message(NOT_REGISTERED));
        }

		if (!foundLandmark.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el POI indicado"));
		}

        Landmark landmarkToUpdate = foundLandmark.get();

        BeanUtils.copyProperties(landmarkDto, landmarkToUpdate, "id", "views", "createDate");
        Landmark updatedLandmark = landmarkService.save(landmarkToUpdate);

        if (updatedLandmark == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("Ha ocurrido un fallo al actualizar el POI."));
        }

        return ResponseEntity.ok(updatedLandmark);
    }

	@GetMapping("/upgrade")
	public ResponseEntity<?> upgradeUser(
		@RequestParam(name="subscriptionDays", defaultValue = "28") long subscriptionDays,
        @RequestParam(name="landmarkId") long landmarkId) {

        if (userService.getCurrentUsername().equals(ANONYMOUS_USER_STRING)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new Message(NOT_REGISTERED));
        }

		Optional<Landmark> foundLandmark = landmarkService.findById(landmarkId);

		if (!foundLandmark.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No se encuentra el landmark indicado."));
		}
		if(subscriptionDays<1){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("No puede subscribirse a días negativos o nulos"));
		}

        Landmark landmark = foundLandmark.get();
		if(landmark.getEndPromotionDate()!=null && landmark.getEndPromotionDate().isAfter(LocalDateTime.now())){
			landmark.setEndPromotionDate(landmark.getEndPromotionDate().plusDays(subscriptionDays));
		} else {
			landmark.setEndPromotionDate(LocalDateTime.now().plusDays(subscriptionDays));
		}
		Landmark updatedLandmark = landmarkService.save(landmark);
		return ResponseEntity.ok(updatedLandmark);
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveLandMark(@Valid @RequestBody LandmarkDto landmarkDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
        }

        String username = userService.getCurrentUsername();

        if (username.equals(ANONYMOUS_USER_STRING)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso para crear POI sin registrarse."));
        }
        
        if (landmarkDto.getLatitude() != null && landmarkDto.getLongitude() == null) {
        	return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("Debe especificar la longitud."));
        }else if (landmarkDto.getLongitude() != null && landmarkDto.getLatitude() == null) {
        	return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("Debe especificar la latitud."));
        }

		Optional<Image> defaultImage = imageService.findById(78);
		if (!defaultImage.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("La imagen seleccionada no ha sido encontrada."));
		}

        Landmark newLandmark = new Landmark();
        BeanUtils.copyProperties(landmarkDto, newLandmark, "id", "views", "createDate", "views", "image");
        newLandmark.setCreateDate(LocalDateTime.now());
        newLandmark.setImage(defaultImage.get());
        newLandmark.setEndPromotionDate(null);
        Landmark createdLandmark = landmarkService.save(newLandmark);
        
        if(landmarkDto.getActivity()!=null) {
            Optional<Activity> foundActivity = activityService.findById(landmarkDto.getActivity());
            if (foundActivity.isPresent()) {
                Activity activity = foundActivity.get();
                activity.setLandmark(newLandmark);
                activityService.save(activity);

                return ResponseEntity.ok(createdLandmark);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("No existe una actividad asociada."));
            }
        }
        return ResponseEntity.ok(createdLandmark);
    }

    @GetMapping("/searchOrderedByViews")
	public ResponseEntity<?> searchOrderedByViews(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String country,
			@RequestParam(defaultValue = "") String city) {
		
		Optional<User> foundUser = userService.getByUsername(userService.getCurrentUsername());

		if (foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN))) {
			Pageable pageable = PageRequest.of(page, size);
			Page<LandmarkProjection> landmarks = landmarkService.searchOrderedByViews("%" + country + "%", "%" + city + "%", pageable);

			return new ResponseEntity<Page<LandmarkProjection>>(landmarks, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new Message("El usuario no tiene permiso para realizar esta consulta."));
		}

	}

}
