package com.yourney.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import com.yourney.model.Activity;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
import com.yourney.model.dto.LandmarkDto;
import com.yourney.model.dto.Message;
import com.yourney.model.projection.LandmarkProjection;
import com.yourney.security.service.UserService;
import com.yourney.service.ActivityService;
import com.yourney.service.LandmarkService;

@RestController
@RequestMapping("/landmark")
public class LandmarkController {

    @Autowired
    private LandmarkService landmarkService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<LandmarkProjection>> listLandMarks() {
        return ResponseEntity.ok(landmarkService.findAllLandmarkProjection());
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showLandMark(@PathVariable("id") long id) {

        if (landmarkService.existsById(id)) {
            Landmark foundLandmark = landmarkService.findById(id).get();

            Long views = foundLandmark.getViews();
            if (views != null) {
                foundLandmark.setViews(views + 1);
                landmarkService.save(foundLandmark);
            } else {
                foundLandmark.setViews((long) 1);
                landmarkService.save(foundLandmark);
            }

            Activity activity = landmarkService.findOneActivityByLandmark(id).orElse(null);

            if (!(foundLandmark.getStatus().equals(StatusType.PUBLISHED)
                    || activity.getItinerary().getAuthor().getUsername().equals(userService.getCurrentUsername()))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new Message("El POI solicitado no se encuentra disponible por decisión de su autor."));
            }

            landmarkService.save(foundLandmark);
            LandmarkProjection foundLandmarkProjection = landmarkService.findOneLandmarkProjection(id).orElse(null);

            return ResponseEntity.ok(foundLandmarkProjection);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el POI indicado."));
        }
    }

    @PutMapping("/update/{id_activity}")
    public ResponseEntity<?> updateLandMark(@RequestBody LandmarkDto landmarkDto,
            @PathVariable("id_activity") long idActivity) {

        String username = userService.getCurrentUsername();

        if (username.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de modficación sin registrarse."));
        }

        Landmark landmarkToUpdate = landmarkService.findById(landmarkDto.getId()).orElse(null);
        Activity activity = activityService.findById(idActivity).orElse(null);

        if (!(landmarkToUpdate.getStatus().equals(StatusType.PUBLISHED)
                || activity.getItinerary().getAuthor().getUsername().equals(userService.getCurrentUsername()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El POI solicitado no se encuentra disponible por decisión de su autor."));
        }

        // UN LANDMARK DEBE TENER ASOCIADA UNA ACTIVIDAD

        if (!activity.getItinerary().getAuthor().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de editar un itinerario que no es suyo."));
        }

        BeanUtils.copyProperties(landmarkDto, landmarkToUpdate, "id", "views", "createDate", "updateDate", "deleteDate",
                "status");

        landmarkToUpdate.setUpdateDate(LocalDateTime.now());

        Landmark updatedLandmark = landmarkService.save(landmarkToUpdate);

        return ResponseEntity.ok(landmarkService.findOneLandmarkProjection(updatedLandmark.getId()));
    }

    @PostMapping("/create/{id_activity}")
    public ResponseEntity<?> saveLandMark(@RequestBody LandmarkDto landmarkDto,
            @PathVariable("id_activity") long idActivity) {

        String username = userService.getCurrentUsername();

        if (username.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso para crear POI sin registrarse."));
        }

        Landmark newLandmark = new Landmark();
        Activity activity = activityService.findById(idActivity).orElse(null);

        BeanUtils.copyProperties(landmarkDto, newLandmark, "id", "views", "createDate", "updateDate", "deleteDate",
                "status", "views");

        newLandmark.setStatus(StatusType.PUBLISHED);
        newLandmark.setCreateDate(LocalDateTime.now());
        newLandmark.setViews((long) 0);
        activity.setLandmark(newLandmark);

        Landmark updatedLandmark = landmarkService.save(newLandmark);
        activityService.save(activity);

        return ResponseEntity.ok(landmarkService.findOneLandmarkProjection(updatedLandmark.getId()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLandMark(@PathVariable("id") long id) {
        if (landmarkService.existsById(id)) {
            String username = userService.getCurrentUsername();
            if (username.equals("anonymousUser")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new Message("El usuario no tiene permiso de eliminar sin registrarse."));
            }

            Landmark landmarkToDelete = landmarkService.findById(id).orElse(null);
            Activity activity = landmarkService.findOneActivityByLandmark(id).orElse(null);

            if (!(landmarkToDelete.getStatus().equals(StatusType.PUBLISHED)
                    || activity.getItinerary().getAuthor().getUsername().equals(userService.getCurrentUsername()))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new Message("El POI solicitado no se encuentra disponible por decisión de su autor."));
            }

            if (!activity.getItinerary().getAuthor().getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new Message("El usuario no tiene permiso de eliminar un POI que no es suyo."));
            }

            Landmark landmark = landmarkService.findById(id).get();
            landmark.setDeleteDate(LocalDateTime.now());
            landmark.setStatus(StatusType.DELETED);
            landmarkService.save(landmark);

            return ResponseEntity.ok(new Message("El POI ha sido eliminado correctamente."));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("No existe el POI seleccionado."));
        }
    }

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
