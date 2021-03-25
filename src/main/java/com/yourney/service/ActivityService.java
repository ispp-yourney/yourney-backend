
package com.yourney.service;

import java.util.Optional;

import com.yourney.model.Activity;
import com.yourney.repository.ActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Iterable<Activity> findAll() {
        return activityRepository.findAll();
    }

    public Optional<Activity> findById(long id) {
        return activityRepository.findById(id);
    }

    public Activity save(Activity activity) {
        Activity newActivity = null;

        try {
            newActivity = activityRepository.save(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newActivity;
    }

    public void deleteById(long id) {
        activityRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return activityRepository.existsById(id);
    }

    public Iterable<Activity> findAllActivityProjectionsByDayAndItinerary(long idItinerary, int dia) {
        return activityRepository.findAllActivityProjectionsByDayAndItinerary(idItinerary,dia);
    }

    public Iterable<Activity> findActivityByItinerary(long idItinerary) {
        return activityRepository.findActivityByItinerary(idItinerary);
    }

    /*

    public Optional<ActivityProjection> findOneActivityProjection(final Long id) {
        return activityRepository.findOneActivityProjection(id);
    }

    public List<ActivityProjection> findAllActivityProjections() {
        return (List<ActivityProjection>) activityRepository.findAllActivityProjections();
    }

    public List<ActivityProjection> findAllActivityProjectionsByDayAndItinerary(long idItinerary, int dia) {
        return (List<ActivityProjection>) activityRepository.findAllActivityProjectionsByDayAndItinerary(idItinerary,
                dia);
    }

    public Iterable<ActivityProjection> findAllActivityProjection() {
        return activityRepository.findAllActivityProjection();
    }

    */
}
