package com.yourney.service;

import java.util.List;
import java.util.Optional;

import com.yourney.model.Image;
import com.yourney.model.projection.ImageProjection;
import com.yourney.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;

    public List<ImageProjection> findByOrderById() {
        return imageRepository.findByOrderById();
    }

    public Optional<Image> findById(long id) {
        return imageRepository.findById(id);
    }

    public boolean existsById(long id) {
        return imageRepository.existsById(id);
    }

    public void save(Image image) {
        imageRepository.save(image);
    }

    public void deleteById(long id) {
        imageRepository.deleteById(id);
    }

    public Optional<Image> findByURL(String url) {
        return imageRepository.findByImageUrl(url);
    }

}
