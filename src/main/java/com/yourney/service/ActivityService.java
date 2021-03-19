
package com.yourney.service;

import java.util.List;
import java.util.Optional;

import com.yourney.model.Activity;
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

    public void save(Activity activity) {
        activityRepository.save(activity);
    }

    public void deleteById(long id) {
        activityRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return activityRepository.existsById(id);
    }
}
