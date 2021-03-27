package com.yourney.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import com.yourney.model.Image;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.dto.ImageDto;
import com.yourney.model.dto.Message;
import com.yourney.model.projection.ImageProjection;
import com.yourney.repository.ItineraryRepository;
import com.yourney.security.service.UserService;
import com.yourney.service.CloudinaryService;
import com.yourney.service.ImageService;
import com.yourney.service.ItineraryService;
import com.yourney.service.LandmarkService;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private LandmarkService landmarkService;

    @Autowired
    private UserService userService;
    /*
    @GetMapping("/list")
    public ResponseEntity<List<ImageProjection>> listImages() {
        return ResponseEntity.ok(imageService.findByOrderById());
    }*/

    @PostMapping("/createForItinerary/{itineraryId}")
    public ResponseEntity<?> newItineraryImage(@PathVariable("itineraryId") Long itineraryId, @RequestBody @Valid ImageDto imageDto, BindingResult result) {
        if (userService.getCurrentUsername().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de creación sin registrarse."));
        }

        if (result.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());

        Image image = new Image();
        BeanUtils.copyProperties(imageDto, image, "id");
        Image createdImage = imageService.save(image);

        Optional<Itinerary> itinerary = itineraryService.findById(itineraryId);
        if(!itinerary.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("El itinerario que intenta asociar a la imagen no existe"));
       
        Itinerary updatedItinerary = itinerary.get();
        if (!userService.getCurrentUsername().equals(updatedItinerary.getAuthor().getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No es posible añadir actividades a un itinerario del que no es dueño."));
        }

        updatedItinerary.setImage(createdImage);
        itineraryService.save(updatedItinerary);

        return ResponseEntity.ok(new Message("La imagen se ha subido correctamente"));
    }

    @PostMapping("/createForLandmark/{landmarkId}")
    public ResponseEntity<?> newLandmarkImage(@PathVariable("landmarkId") Long landmarkId, @RequestBody @Valid ImageDto imageDto, BindingResult result) {

        if (userService.getCurrentUsername().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de creación sin registrarse."));
        }

        if (result.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());

        Image image = new Image();
        BeanUtils.copyProperties(imageDto, image, "id");
        Image createdImage = imageService.save(image);

        Optional<Landmark> landmark = landmarkService.findById(landmarkId);
        if(!landmark.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("El POI al que intenta asociar la imagen no existe"));
    
        Landmark updatedLandmark = landmark.get();
        updatedLandmark.setImage(createdImage);
        landmarkService.save(updatedLandmark);

        return ResponseEntity.ok(new Message("La imagen se ha subido correctamente"));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        if (userService.getCurrentUsername().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de creación sin registrarse."));
        }

        if (ImageIO.read(multipartFile.getInputStream()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("La imagen no es válida"));
        }
        Map<?, ?> result = cloudinaryService.upload(multipartFile);

        Image image = new Image((String) result.get("original_filename"), (String) result.get("url"),
                (String) result.get("public_id"));
        imageService.save(image);

        return ResponseEntity.ok(new Message("La imagen se ha subido correctamente"));
    }

    @DeleteMapping("{id}/deleteFromItinerary/{itineraryId}")
    public ResponseEntity<?> deleteFromItinerary(@PathVariable("id") long id,@PathVariable("itineraryId") long itineraryId) throws IOException {
        if (userService.getCurrentUsername().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de eliminación sin registrarse."));
        }
        
        Optional<Image> image = imageService.findById(id);
        if (!image.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("No existe la imagen con el id indicado"));
        }

        Optional<Itinerary> itinerary = itineraryService.findById(itineraryId);
        if(!itinerary.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("El itinerario que intenta asociar a la imagen no existe"));
        
        Itinerary updatedItinerary = itinerary.get();

        if(id != updatedItinerary.getImage().getId()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("El itinerario que intenta eliminar no contiene la imagen seleccionada."));
        }
        if(updatedItinerary.getImage()==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("El itienerario seleccionado no tiene imágenes asociadas"));
        }

        if (!userService.getCurrentUsername().equals(updatedItinerary.getAuthor().getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No es posible eliminar imágenes a un itinerario del que no es dueño."));
        }
        updatedItinerary.setImage(null);
        itineraryService.save(updatedItinerary);

        if (image.get().getCloudinaryId() != null) {
            cloudinaryService.delete(image.get().getCloudinaryId());
        }

        imageService.deleteById(id);

        return ResponseEntity.ok(new Message("Imagen eliminada correctamente"));
    }

    @DeleteMapping("{id}/deleteFromLandmark/{landmarkId}")
    public ResponseEntity<?> deleteFromLandmark(@PathVariable("id") long id,@PathVariable("landmarkId") long landmarkId) throws IOException {
        if (userService.getCurrentUsername().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de eliminación sin registrarse."));
        }
        
        Optional<Image> image = imageService.findById(id);
        if (!image.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("No existe la imagen con el id indicado"));
        }

        Optional<Landmark> landmark = landmarkService.findById(landmarkId);
        if(!landmark.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("El POI que intenta asociar a la imagen no existe"));
        
        Landmark updatedLandmark = landmark.get();

        if(updatedLandmark.getImage()==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("El POI seleccionado no tiene imágenes asociadas"));
        }
        if(id != updatedLandmark.getImage().getId()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("El POI que intenta eliminar no contiene la imagen seleccionada."));
        }

        updatedLandmark.setImage(null);
        landmarkService.save(updatedLandmark);

        if (image.get().getCloudinaryId() != null) {
            cloudinaryService.delete(image.get().getCloudinaryId());
        }

        imageService.deleteById(id);

        return ResponseEntity.ok(new Message("Imagen eliminada correctamente"));
    }
}
