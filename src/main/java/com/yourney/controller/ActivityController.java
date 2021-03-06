package com.yourney.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import com.yourney.model.Activity;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
import com.yourney.model.dto.ActivityDto;
import com.yourney.model.dto.Message;
import com.yourney.security.model.RoleType;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;
import com.yourney.service.ActivityService;
import com.yourney.service.ItineraryService;
import com.yourney.service.LandmarkService;
import com.yourney.utils.ValidationUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/activity")
@CrossOrigin
public class ActivityController {

	private static final String ACTIVITY_NOT_FOUND = "No existe la actividad indicada";
	
    @Autowired
    private ActivityService activityService;

    @Autowired
    private ItineraryService itineraryService;
    
    @Autowired
    private LandmarkService landmarkService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<Activity>> listActivitiesByItineraryAndDay(
            @RequestParam(defaultValue = "0") int itineraryId, @RequestParam(defaultValue = "1") int day) {

        Iterable<Activity> activitiesList = activityService.findAllActivityProjectionsByDayAndItinerary(itineraryId,
                day);

        return new ResponseEntity<>(activitiesList, HttpStatus.OK);
    }

    @GetMapping("/listByItinerary")
    public ResponseEntity<Iterable<Activity>> listActivitiesByItinerary(
            @RequestParam(defaultValue = "0") int itineraryId) {

        Iterable<Activity> activitiesList = activityService.findActivityByItinerary(itineraryId);

        return new ResponseEntity<>(activitiesList, HttpStatus.OK);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showActivity(@PathVariable("id") long id) {
        Optional<Activity> foundActivity = activityService.findById(id);

        if (!foundActivity.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe la actividad indicada."));
        }

        Activity activity = foundActivity.get();

        if (activity.getItinerary().getStatus().equals(StatusType.DRAFT)
                && !activity.getItinerary().getAuthor().getUsername().equals(userService.getCurrentUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No tiene permisos para ver esta actividad."));
        }

        return ResponseEntity.ok(activity);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createActivity(@Valid @RequestBody ActivityDto activityDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
        }
        String username = userService.getCurrentUsername();

        if (username.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de creaci??n sin registrarse."));
        }

        Optional<Itinerary> findItinerary = itineraryService.findById(activityDto.getItinerary());

        if (!findItinerary.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El itinerario indicado para la actividad no existe"));
        }

        Itinerary itinerary = findItinerary.get();

        if (!username.equals(itinerary.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No es posible a??adir actividades a un itinerario del que no es due??o."));
        }

        Activity newActivity = new Activity();
        BeanUtils.copyProperties(activityDto, newActivity, "id", "createDate");

        newActivity.setItinerary(itinerary);
        newActivity.setCreateDate(LocalDateTime.now());
        
        if (activityDto.getLandmark() != 0) {
        	
        	Optional<Landmark> findLandmark = landmarkService.findById(activityDto.getLandmark());
            
            if (!findLandmark.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Message("El punto de inter??s indicado para la actividad no existe"));
            }
            
            Landmark landmark = findLandmark.get();
            
            newActivity.setLandmark(landmark);
     
        }
        
        Activity createdActivity = activityService.save(newActivity);
        return ResponseEntity.ok(createdActivity);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateActivity(@Valid @RequestBody ActivityDto activityDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
        }

        String username = userService.getCurrentUsername();
        Optional<Activity> foundActivity = activityService.findById(activityDto.getId());

        if (!foundActivity.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message(ACTIVITY_NOT_FOUND));
        }

        Activity activityToUpdate = foundActivity.get();
        Optional<User> foundUser = userService.getByUsername(userService.getCurrentUsername());

        if (!activityService.existsById(activityDto.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(ACTIVITY_NOT_FOUND));
        }

        if (!username.equals(activityToUpdate.getItinerary().getAuthor().getUsername()) && !(foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN)))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No puede a??adir una actividad a un itinerario del que no es due??o."));
        }

        BeanUtils.copyProperties(activityDto, activityToUpdate, "id");

        activityService.save(activityToUpdate);
        return ResponseEntity.ok(new Message("La actividad ha sido actualizada con ??xito"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable("id") long id) {
        String username = userService.getCurrentUsername();
        Optional<Activity> foundActivity = activityService.findById(id);

        if (!foundActivity.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(ACTIVITY_NOT_FOUND));
        }

        Activity activityToDelete = foundActivity.get();
        Optional<User> foundUser = userService.getByUsername(userService.getCurrentUsername());

        if (!username.equals(activityToDelete.getItinerary().getAuthor().getUsername()) && !(foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN)))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No puede eliminar una actividad de un itinerario del que no es creador."));
        }
        activityService.deleteById(activityToDelete.getId());

        return ResponseEntity.ok(new Message("Actividad eliminada correctamente"));
    }
}
