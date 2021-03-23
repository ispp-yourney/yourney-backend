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

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.yourney.model.Activity;
import com.yourney.model.Landmark;
import com.yourney.model.dto.LandmarkDto;
import com.yourney.model.dto.Message;
import com.yourney.model.projection.LandmarkProjection;
import com.yourney.security.service.UserService;
import com.yourney.service.LandmarkService;

@RestController
@RequestMapping("/landmark")
public class LandmarkController {
    
    @Autowired
    private LandmarkService landmarkService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<Landmark>> listLandMarks() {
        return ResponseEntity.ok(landmarkService.findAllLandmarks());
    }
    
    @GetMapping("/show/{id}")
    public ResponseEntity<?> showLandMark(@PathVariable("id") long id) {

    
        if(landmarkService.existsById(id)){        
            Landmark foundLandmark = landmarkService.findById(id).get();

            Long views = foundLandmark.getViews();
            if(views!=null){
                foundLandmark.setViews(views+1);
                landmarkService.save(foundLandmark);
            }

            landmarkService.save(foundLandmark);
            LandmarkProjection foundLandmarkProjection = landmarkService.findOneLandmarkProjection(id).orElse(null);

            return ResponseEntity.ok(foundLandmarkProjection);
        } else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el POI indicado."));
		}
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLandMark(@RequestBody LandmarkDto landmarkDto) {
        
		Landmark landmarkToUpdate = landmarkService.findById(landmarkDto.getId()).orElse(null);

        BeanUtils.copyProperties(landmarkDto, landmarkToUpdate, "id", "views", "createDate", "updateDate", "deleteDate");

		landmarkToUpdate.setUpdateDate(LocalDateTime.now());

		Landmark updatedLandmark = landmarkService.save(landmarkToUpdate);

		return ResponseEntity.ok(landmarkService.findOneLandmarkProjection(updatedLandmark.getId()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveLandMark(@RequestBody LandmarkDto landmarkDto) {
        
		Landmark newLandmark = new Landmark();

        BeanUtils.copyProperties(landmarkDto, newLandmark, "id", "views", "createDate", "updateDate", "deleteDate");

		newLandmark.setCreateDate(LocalDateTime.now());

		Landmark updatedLandmark = landmarkService.save(newLandmark);

		return ResponseEntity.ok(landmarkService.findOneLandmarkProjection(updatedLandmark.getId()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLandMark(@PathVariable("id") long id) {
        if(landmarkService.existsById(id)){
            String username = userService.getCurrentUsername();
            if(username.equals("anonymousUser")){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("El usuario no tiene permiso de eliminar sin registrarse."));
            }
            Activity activity = landmarkService.findOneActivityByLandmark(id).orElse(null);
            if(!activity.getItinerary().getAuthor().getUsername().equals(username)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("El usuario no tiene permiso de eliminar un itinerario que no es suyo."));
            }
            
            Landmark landmark = landmarkService.findById(id).get();
            landmark.setDeleteDate(LocalDateTime.now());
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
