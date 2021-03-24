package com.yourney.controller;

import java.time.LocalDateTime;

import com.yourney.model.Activity;
import com.yourney.model.Itinerary;
import com.yourney.model.dto.ActivityDto;
import com.yourney.model.dto.Message;
import com.yourney.service.ActivityService;
import com.yourney.service.ItineraryService;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
@CrossOrigin
public class ActivityController {
    
    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private ActivityService activityService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<Activity>> listActivities() {
        return ResponseEntity.ok(activityService.findAll());
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<Activity> showActivity(@PathVariable("id") long id) {
        return ResponseEntity.ok(activityService.findById(id).orElse(null));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createActivity(@RequestBody ActivityDto activityDto) {
        Activity newActivity = new Activity();
        BeanUtils.copyProperties(activityDto, newActivity, "id");

        if (!itineraryService.existsById(activityDto.getItineraryId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El itinerario indicado para la actividad no existe");    
        }
        
        Itinerary itinerary = itineraryService.findById(activityDto.getItineraryId()).orElse(null);
        newActivity.setItinerary(itinerary);
        newActivity.setCreateDate(LocalDateTime.now());
        activityService.save(newActivity);
        return ResponseEntity.ok(new Message("Se ha creado la actividad correctamente"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateActivity(@RequestBody ActivityDto activityDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldError());
        } else if (!activityService.existsById(activityDto.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe la actividad indicada"));
        }
        

        Activity activityToUpdate = activityService.findById(activityDto.getId()).orElse(null);

        BeanUtils.copyProperties(activityDto, activityToUpdate, "id");
        
        activityService.save(activityToUpdate);
        return ResponseEntity.ok(new Message("La actividad ha sido creada con exito"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable("id") long id) {
        if (!activityService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No exites la actividad indicada"));
        }

        activityService.deleteById(id);
        return ResponseEntity.ok(new Message("Actividad eliminada correctamente"));
    }
}
