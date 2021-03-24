
package com.yourney.service;

import java.util.List;
import java.util.Optional;

import com.yourney.model.Activity;
import com.yourney.model.projection.ActivityProjection;
import com.yourney.repository.ActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> findAll() {
        return (List<Activity>) activityRepository.findAll();
    }

    public Optional<Activity> findById(long id) {
        return activityRepository.findById(id);
    }

    public Activity save(Activity activity) {
        Activity newActivity = activityRepository.save(activity);
        return newActivity;
    }

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

    public void deleteById(long id) {
        activityRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return activityRepository.existsById(id);
    }
}
