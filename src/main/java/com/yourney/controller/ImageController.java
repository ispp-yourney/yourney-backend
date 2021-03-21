package com.yourney.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import com.yourney.model.Image;
import com.yourney.model.dto.ImageDto;
import com.yourney.model.dto.Message;
import com.yourney.model.projection.ImageProjection;
import com.yourney.service.CloudinaryService;
import com.yourney.service.ImageService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {
    
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/list")
    public ResponseEntity<List<ImageProjection>> listImages() {
        return ResponseEntity.ok(imageService.findByOrderById());
    }

    @PostMapping("/create")
    public ResponseEntity<?> newImage(@RequestBody @Valid ImageDto imageDto, BindingResult result) {
        
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());  
        }
        
        Image image = new Image();
        BeanUtils.copyProperties(imageDto, image);
        imageService.save(image);

        return ResponseEntity.ok(new Message("La imagen se ha subido correctamente"));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        if (ImageIO.read(multipartFile.getInputStream()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("La imagen no es válida"));    
        }
        Map<?,?> result = cloudinaryService.upload(multipartFile);

        Image image = new Image((String) result.get("original_filename"), (String) result.get("url"), (String) result.get("public_id"));
        imageService.save(image);

        return ResponseEntity.ok(new Message("La imagen se ha subido correctamente"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) throws IOException {
        if (!imageService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe la imagen con el id indicado"));
        }

        Image image = imageService.findById(id).get();

        if (image.getImageId() != null) {
            cloudinaryService.delete(image.getImageId());
        }

        imageService.deleteById(id);

        return ResponseEntity.ok(new Message("Imagen eliminada correctamente"));
    }
}
