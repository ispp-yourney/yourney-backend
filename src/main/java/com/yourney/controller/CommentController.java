
package com.yourney.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import com.yourney.model.Comment;
import com.yourney.model.Itinerary;
import com.yourney.model.StatusType;
import com.yourney.model.dto.CommentDto;
import com.yourney.model.dto.Message;
import com.yourney.security.model.RoleType;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;
import com.yourney.service.CommentService;
import com.yourney.service.ItineraryService;
import com.yourney.utils.ValidationUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDto commentDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
        }
        String username = userService.getCurrentUsername();

        if (username.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("El usuario no tiene permiso de comentar un itinerario sin registrarse"));
        }

        Optional<Itinerary> findItinerary = itineraryService.findById(commentDto.getItinerary());

        if (!findItinerary.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El itinerario que se quiere comentar no existe"));
        }

        Itinerary itinerary = findItinerary.get();
        
        if (username.equals(itinerary.getAuthor().getUsername())) {
        	 return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(new Message("Un usuario no puede comentar sus propios itinerarios"));
        }

        if (itinerary.getComments() != null) {
        	for (Comment comment : itinerary.getComments()) {
    	        if (username.equals(comment.getAuthor().getUsername())) {
    	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
    	                    .body(new Message("El usuario ya ha realizado un comentario en este itinerario"));
    	        }
            }
        }
       

        if (!itinerary.getStatus().equals(StatusType.PUBLISHED)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).
            body(new Message("No tiene permisos para comentar este itinerario"));
        }

        Optional<User> findAuthor = userService.getByUsername(username);
        
        if (!findAuthor.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("El usuario actual no existe"));
        }
        
        User author = findAuthor.get();

        Comment newComment = new Comment();
        BeanUtils.copyProperties(commentDto, newComment, "itinerary");

        newComment.setItinerary(itinerary);
        newComment.setAuthor(author);
        newComment.setCreateDate(LocalDateTime.now());
        Comment createdComment = commentService.save(newComment);
        return ResponseEntity.ok(createdComment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") long id) {
        String username = userService.getCurrentUsername();
        Optional<Comment> foundComment = commentService.findById(id);

        if (!foundComment.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el comentario indicado"));
        }

        Comment commentToDelete = foundComment.get();
        Optional<User> foundUser = userService.getByUsername(username);

        if (!username.equals(commentToDelete.getAuthor().getUsername()) && !(foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN)))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Message("No puede eliminar un comentario de un itinerario del que no es creador"));
        }
        commentService.deleteById(commentToDelete.getId());

        return ResponseEntity.ok(new Message("Comentario eliminado correctamente"));
    }
    
}
