package com.yourney.controller;

import java.time.LocalDateTime;

import com.yourney.model.Activity;
import com.yourney.model.Itinerary;
import com.yourney.model.StatusType;
import com.yourney.model.dto.ActivityDto;
import com.yourney.model.dto.Message;
import com.yourney.model.projection.ActivityProjection;
import com.yourney.security.service.UserService;
import com.yourney.service.ActivityService;
import com.yourney.service.ItineraryService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<ActivityProjection>> listActivities() {
        Iterable<ActivityProjection> activitiesList = activityService.findAllActivityProjection();
        return new ResponseEntity<>(activitiesList, HttpStatus.OK);
    }

    @GetMapping("/list/{id}/{dia}")
    public ResponseEntity<Iterable<ActivityProjection>> listActivities(@PathVariable("id") long id,
            @PathVariable("dia") int dia) {
        Iterable<ActivityProjection> activitiesList = activityService.findAllActivityProjectionsByDayAndItinerary(id,
                dia);
        return new ResponseEntity<>(activitiesList, HttpStatus.OK);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showActivity(@PathVariable("id") long id) {

        if (activityService.existsById(id)) {

            Activity foundActivity = activityService.findById(id).get();

            if (!foundActivity.getStatus().equals(StatusType.PUBLISHED)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new Message("La actividad no se encuentra publicada en este momento."));
            }

            Integer views = foundActivity.getViews();
            if (views != null) {
                foundActivity.setViews(foundActivity.getViews() + 1);
                activityService.save(foundActivity);
            } else {
                foundActivity.setViews(1);
                activityService.save(foundActivity);
            }
            ActivityProjection foundActivityProjection = activityService.findOneActivityProjection(id).orElse(null);
            return ResponseEntity.ok(foundActivityProjection);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe la actividad indicada."));
        }

    }

    @PostMapping("/create")
    public ResponseEntity<?> createActivity(@RequestBody ActivityDto activityDto) {
        String username = userService.getCurrentUsername();
        Activity newActivity = new Activity();

        if (username.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de creación sin registrarse."));
        }

        if (!itineraryService.existsById(activityDto.getItinerary())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El itinerario indicado para la actividad no existe");
        }

        Itinerary itinerary = itineraryService.findById(activityDto.getItinerary()).orElse(null);

        if (!username.equals(itinerary.getAuthor().getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No es posible añadir actividades a un itinerario del que no es dueño."));
        }

        if (itinerary != null && itinerary.getStatus().equals(StatusType.DELETED)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El itinerario indicado para la actividad ha sido borrado");
        }

        if (itinerary != null && itinerary.getStatus().equals(StatusType.DRAFT)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El itinerario indicado para la actividad no está publicado");
        }

        BeanUtils.copyProperties(activityDto, newActivity, "id", "status", "deleteDate", "updateDate", "createDate",
                "views");

        newActivity.setViews(0);
        newActivity.setItinerary(itinerary);
        newActivity.setCreateDate(LocalDateTime.now());
        newActivity.setStatus(StatusType.PUBLISHED);
        Activity createdActivity = activityService.save(newActivity);
        return ResponseEntity.ok(showActivity(createdActivity.getId()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateActivity(@RequestBody ActivityDto activityDto, BindingResult result) {
        String username = userService.getCurrentUsername();
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldError());

        } else if (!activityService.existsById(activityDto.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe la actividad indicada"));
        }

        Activity foundActivity = activityService.findById(activityDto.getId()).get();

        if (!foundActivity.getStatus().equals(StatusType.PUBLISHED)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("La actividad no se encuentra publicada en este momento y por tanto no se puede editar.");
        }

        if (username.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de actualizar una actividad sin registrarse."));
        }

        if (!itineraryService.existsById(activityDto.getItinerary())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El nuevo itinerario indicado para la actividad no existe");
        }

        Itinerary itinerary = itineraryService.findById(activityDto.getItinerary()).orElse(null);

        if (!username.equals(itinerary.getAuthor().getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No puede añadir una actividad a un itinerario del que no es dueño."));
        }

        if (itinerary != null && itinerary.getStatus().equals(StatusType.DELETED)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El nuevo itinerario indicado para la actividad ha sido borrado");
        }

        if (itinerary != null && itinerary.getStatus().equals(StatusType.DRAFT)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El nuevo itinerario indicado para la actividad no está publicado");
        }

        Activity activityToUpdate = activityService.findById(activityDto.getId()).orElse(null);

        BeanUtils.copyProperties(activityDto, activityToUpdate, "id");

        activityToUpdate.setItinerary(itinerary);
        activityToUpdate.setUpdateDate(LocalDateTime.now());

        activityService.save(activityToUpdate);
        return ResponseEntity.ok(new Message("La actividad ha sido actualizada con éxito"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable("id") long id) {
        String username = userService.getCurrentUsername();
        Activity activityToDelete = activityService.findById(id).orElse(null);

        if (username.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de eliminación sin registrarse."));
        }

        if (!activityService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No exite la actividad indicada"));
        }

        Activity foundActivity = activityService.findById(id).get();

        if (!foundActivity.getStatus().equals(StatusType.PUBLISHED)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("La actividad no se encuentra publicada en este momento y por tanto no se puede borrar.");
        }

        Itinerary itinerary = itineraryService.findById(activityToDelete.getItinerary().getId()).orElse(null);

        if (!username.equals(itinerary.getAuthor().getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No puede eliminar una actividad de un itinerario del que no es creador."));
        }

        if (itinerary != null && itinerary.getStatus().equals(StatusType.DELETED)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede eliminar una actividad de un itinerario que ha sido borrado");
        }

        if (itinerary != null && itinerary.getStatus().equals(StatusType.DRAFT)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede eliminar una actividad de un itinerario que está en borrador.");
        }

        Activity activity = activityService.findById(id).get();
        activity.setDeleteDate(LocalDateTime.now());
        activity.setStatus(StatusType.DELETED);
        activityService.save(activity);

        return ResponseEntity.ok(new Message("Actividad eliminada correctamente"));
    }
}
