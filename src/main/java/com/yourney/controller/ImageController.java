package com.yourney.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import com.yourney.model.Image;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.dto.Message;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;
import com.yourney.service.CloudinaryService;
import com.yourney.service.ImageService;
import com.yourney.service.ItineraryService;
import com.yourney.service.LandmarkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    private static final String ANONYMOUS_USER_STRING = "anonymousUser";
    private static final String ERROR_USUARIO_NO_REGISTRADO_STRING = "El usuario no tiene permiso de creación sin registrarse.";
    private static final String IMAGEN_SUBIDA_STRING = "La imagen se ha subido correctamente"; 

    @PostMapping("/uploadForItinerary/{itineraryId}")
    public ResponseEntity<?> newItineraryImage(@PathVariable("itineraryId") Long itineraryId,
        @RequestParam MultipartFile multipartFile) throws IOException {

        if (userService.getCurrentUsername().equals(ANONYMOUS_USER_STRING)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message(ERROR_USUARIO_NO_REGISTRADO_STRING));
        }

        if (ImageIO.read(multipartFile.getInputStream()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("La imagen no es válida"));
        }
        Map<?, ?> result = cloudinaryService.upload(multipartFile);

        Image image = new Image((String) result.get("original_filename"), (String) result.get("url"),
                (String) result.get("public_id"));
        Image createdImage = imageService.save(image);

        Optional<Itinerary> itinerary = itineraryService.findById(itineraryId);
        if (!itinerary.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El itinerario que intenta asociar a la imagen no existe"));
        }
        Itinerary updatedItinerary = itinerary.get();
        if (!userService.getCurrentUsername().equals(updatedItinerary.getAuthor().getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No es posible añadir imágenes a un itinerario del que no es dueño."));
        }

        Image currentItineraryImage = updatedItinerary.getImage();

        updatedItinerary.setImage(createdImage);
        itineraryService.save(updatedItinerary);

        if(currentItineraryImage!=null && currentItineraryImage.getCloudinaryId() != null && currentItineraryImage.getId()!=1){
            cloudinaryService.delete(currentItineraryImage.getCloudinaryId());
        }
        if(currentItineraryImage.getId()!=1){
            imageService.deleteById(currentItineraryImage.getId());
        }

        return ResponseEntity.ok(new Message(IMAGEN_SUBIDA_STRING));
    }

    @PostMapping("/uploadForLandmark/{landmarkId}")
    public ResponseEntity<?> newLandmarkImage(@PathVariable("landmarkId") Long landmarkId,
        @RequestParam MultipartFile multipartFile) throws IOException {

        if (userService.getCurrentUsername().equals(ANONYMOUS_USER_STRING)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message(ERROR_USUARIO_NO_REGISTRADO_STRING));
        }

        if (ImageIO.read(multipartFile.getInputStream()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("La imagen no es válida"));
        }
        Map<?, ?> result = cloudinaryService.upload(multipartFile);

        Image image = new Image((String) result.get("original_filename"), (String) result.get("url"),
                (String) result.get("public_id"));
        Image createdImage = imageService.save(image);

        Optional<Landmark> landmark = landmarkService.findById(landmarkId);
        if (!landmark.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El POI que intenta asociar a la imagen no existe"));
        }
        Landmark updatedLandmark = landmark.get();

        Image currentLandmarkImage = updatedLandmark.getImage();

        
        updatedLandmark.setImage(createdImage);
        landmarkService.save(updatedLandmark);

        if(currentLandmarkImage!=null && currentLandmarkImage.getCloudinaryId() != null && currentLandmarkImage.getId()!=1){
            cloudinaryService.delete(currentLandmarkImage.getCloudinaryId());
        }
        if(currentLandmarkImage.getId()!=1){
            imageService.deleteById(currentLandmarkImage.getId());
        }

        return ResponseEntity.ok(new Message(IMAGEN_SUBIDA_STRING));
    }

    @PostMapping("/uploadForUser")
    public ResponseEntity<?> newLandmarkImage(@RequestParam MultipartFile multipartFile) throws IOException {

        if (userService.getCurrentUsername().equals(ANONYMOUS_USER_STRING)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message(ERROR_USUARIO_NO_REGISTRADO_STRING));
        }

        if (ImageIO.read(multipartFile.getInputStream()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("La imagen no es válida"));
        }
        Map<?, ?> result = cloudinaryService.upload(multipartFile);

        Image image = new Image((String) result.get("original_filename"), (String) result.get("url"),
                (String) result.get("public_id"));
        Image createdImage = imageService.save(image);

        Optional<User> user = userService.getByUsername(userService.getCurrentUsername());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El usuario al que desea asignar la imagen no existe."));
        }
        User updatedUser = user.get();

        Image currentUserImage = updatedUser.getImage();

        updatedUser.setImage(createdImage);
        userService.save(updatedUser);

        if(currentUserImage!=null && currentUserImage.getCloudinaryId() != null && currentUserImage.getId()!=1){
            cloudinaryService.delete(currentUserImage.getCloudinaryId());
        }
        if(currentUserImage.getId()!=1){
            imageService.deleteById(currentUserImage.getId());
        }

        return ResponseEntity.ok(new Message(IMAGEN_SUBIDA_STRING));
    }    

    @DeleteMapping("/deleteForItinerary/{itineraryId}")
    public ResponseEntity<?> deleteFromItinerary(@PathVariable("itineraryId") long itineraryId) throws IOException {
        if (userService.getCurrentUsername().equals(ANONYMOUS_USER_STRING)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de eliminación sin registrarse."));
        }

        Optional<Itinerary> itinerary = itineraryService.findById(itineraryId);
        if (!itinerary.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El itinerario sobre el que intenta eliminar la imagen no existe"));
        }
        Itinerary updatedItinerary = itinerary.get();

        Image image = updatedItinerary.getImage();
        if (image==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El itinerario indicado no contiene ninguna imagen"));
        }

        if (!userService.getCurrentUsername().equals(updatedItinerary.getAuthor().getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No es posible eliminar imágenes a un itinerario del que no es dueño."));
        }

        Optional<Image> defaultImage = imageService.findById(1);
		if (!defaultImage.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("La imagen seleccionada no ha sido encontrada."));
		}
        updatedItinerary.setImage(defaultImage.get());
        itineraryService.save(updatedItinerary);

        if (image.getCloudinaryId() != null && image.getId() != 1) {
            cloudinaryService.delete(image.getCloudinaryId());
        }
        if(image.getId()!=1){
            imageService.deleteById(image.getId());
        }  else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El itinerario no tiene ninguna foto asociada."));
        }
        
        return ResponseEntity.ok(new Message("Imagen eliminada correctamente"));
    }

    @DeleteMapping("/deleteForLandmark/{landmarkId}")
    public ResponseEntity<?> deleteFromLandmark(@PathVariable("landmarkId") long landmarkId) throws IOException {
        if (userService.getCurrentUsername().equals(ANONYMOUS_USER_STRING)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de eliminación sin registrarse."));
        }

        Optional<Landmark> landmark = landmarkService.findById(landmarkId);
        if (!landmark.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El itinerario sobre el que intenta eliminar la imagen no existe"));
        }
        Landmark updatedLandmark = landmark.get();

        Image image = updatedLandmark.getImage();
        if (image==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El POI indicado no contiene ninguna imagen"));
        }

        Optional<Image> defaultImage = imageService.findById(1);
		if (!defaultImage.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("La imagen seleccionada no ha sido encontrada."));
		}
        updatedLandmark.setImage(defaultImage.get());
        landmarkService.save(updatedLandmark);

        if (image.getCloudinaryId() != null && image.getId() != 1) {
            cloudinaryService.delete(image.getCloudinaryId());
        }
        if(image.getId()!=1){
            imageService.deleteById(image.getId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El POI no tiene ninguna foto asociada."));
        }
        
        return ResponseEntity.ok(new Message("Imagen eliminada correctamente"));
    }

    @DeleteMapping("/deleteForUser")
    public ResponseEntity<?> deleteFromUser() throws IOException {
        if (userService.getCurrentUsername().equals(ANONYMOUS_USER_STRING)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de eliminación sin registrarse."));
        }
        
        Optional<User> user = userService.getByUsername(userService.getCurrentUsername());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El usuario al que desea asignar la imagen no existe."));
        }
        User updatedUser = user.get();
        Image currentUserImage = updatedUser.getImage();

        Optional<Image> defaultImage = imageService.findById(1);
		if (!defaultImage.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("La imagen seleccionada no ha sido encontrada."));
		}

        updatedUser.setImage(defaultImage.get());
        userService.save(updatedUser);

        if(currentUserImage!=null && currentUserImage.getCloudinaryId() != null && currentUserImage.getId()!=1){
            cloudinaryService.delete(currentUserImage.getCloudinaryId());
        }
        if(currentUserImage.getId()!=1){
            imageService.deleteById(currentUserImage.getId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El usuario no tiene ninguna foto asociada."));
        }

        return ResponseEntity.ok(new Message("Imagen eliminada correctamente"));
    }
}
