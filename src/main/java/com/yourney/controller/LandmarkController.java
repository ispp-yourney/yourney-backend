package com.yourney.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import java.util.Optional;

import javax.validation.Valid;

import com.yourney.model.Activity;
import com.yourney.model.Image;
import com.yourney.model.Landmark;
import com.yourney.model.dto.LandmarkDto;
import com.yourney.model.dto.Message;
import com.yourney.model.projection.LandmarkProjection;
import com.yourney.security.service.UserService;
import com.yourney.service.ActivityService;
import com.yourney.service.ImageService;
import com.yourney.service.LandmarkService;
import com.yourney.utils.ValidationUtils;

@RestController
@RequestMapping("/landmark")
@CrossOrigin
public class LandmarkController {

    @Autowired
    private LandmarkService landmarkService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    private static final String ANONYMOUS_USER_STRING = "anonymousUser";

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

	@GetMapping("/search")
	public ResponseEntity<Iterable<LandmarkProjection>> searchByProperties(
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "") String country,
			@RequestParam(defaultValue = "") String city, @RequestParam(defaultValue = "") String name) {

		Iterable<LandmarkProjection> landmarks = landmarkService.searchByProperties("%" + country + "%",
				"%" + city + "%","%" + name + "%", size, PageRequest.of(page, size));

		return new ResponseEntity<Iterable<LandmarkProjection>>(landmarks, HttpStatus.OK);
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

        landmarkService.deleteById(landmarkToDelete.getId());

        return ResponseEntity.ok(new Message("El punto de interés ha sido eliminado correctamente."));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showLandMark(@PathVariable("id") long id) {

        Optional<Landmark> landmark = landmarkService.findById(id);

        if (landmark.isPresent()) {
            Landmark foundLandmark = landmark.get();

            Long views = foundLandmark.getViews();
            if (views != null) {
                foundLandmark.setViews(views + 1);
                landmarkService.save(foundLandmark);
            } else {
                foundLandmark.setViews((long) 1);
                landmarkService.save(foundLandmark);
            }
            landmarkService.save(foundLandmark);

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
                    .body(new Message("El usuario no tiene permiso de modificación sin registrarse."));
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
                        .body(new Message("El usuario no tiene permiso de modificación sin registrarse."));
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

		Optional<Image> defaultImage = imageService.findById(78);
		if (!defaultImage.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("La imagen seleccionada no ha sido encontrada."));
		}

        Landmark newLandmark = new Landmark();
        BeanUtils.copyProperties(landmarkDto, newLandmark, "id", "views", "createDate", "views", "image");
        newLandmark.setCreateDate(LocalDateTime.now());
        newLandmark.setViews((long) 0);
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

}
