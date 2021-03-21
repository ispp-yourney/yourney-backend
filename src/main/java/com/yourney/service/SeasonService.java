
package com.yourney.service;

import java.util.List;
import java.util.Optional;

import com.yourney.model.Season;
import com.yourney.repository.SeasonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeasonService {
    
    @Autowired
    private SeasonRepository seasonRepository;

    public List<Season> findAll() {
        return (List<Season>) seasonRepository.findAll();
    }

    public Optional<Season> findById(long id) {
        return seasonRepository.findById(id);
    }

    public void save(Season season) {
        seasonRepository.save(season);
    }

    public void deleteById(long id) {
        seasonRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return seasonRepository.existsById(id);
    }
}
