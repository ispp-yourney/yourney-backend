package com.yourney.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.yourney.model.Activity;
import com.yourney.model.Comment;
import com.yourney.model.Image;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
import com.yourney.model.projection.ItineraryProjection;
import com.yourney.security.model.Role;
import com.yourney.security.model.RoleType;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;
import com.yourney.service.ActivityService;
import com.yourney.service.CommentService;
import com.yourney.service.ImageService;
import com.yourney.service.ItineraryService;
import com.yourney.service.LandmarkService;


@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTests {

	private static final int TEST_COMMENT_ID_1 = 1;
	private static final int TEST_COMMENT_ID_NOT_FOUND = 4;
	private static final int TEST_ITINERARY_ID_1 = 1;
	private static final int TEST_ITINERARY_ID_NOT_FOUND = 10;
	private static final int TEST_ITINERARY_PAGE = 1;
	private static final int TEST_ITINERARY_SIZE = 10;
	private static final String TEST_ITINERARY_COUNTRY_1 = "Brazil";
	private static final String TEST_ITINERARY_CITY_1 = "Rio de Janeiro";
	private static final String TEST_ITINERARY_COUNTRY_2 = "Francia";
	private static final String TEST_ITINERARY_CITY_2 = "ParÃ­s";
	private static final Double TEST_ITINERARY_MAXBUDGET = 9000.;
	private static final Double TEST_ITINERARY_LATITUDE = 60.0;
	private static final Double TEST_ITINERARY_LONGITUDE = 60.0;
	private static final Integer TEST_ITINERARY_MAXDAYS = 1000;
	private static final String TEST_ITINERARY_NAME = "itinerary test 1";
	private static final int TEST_ITINERARY_USER_ID = 1;
	private static final String TEST_ITINERARY_USERNAME = "user1";
	
	@Autowired
	protected CommentController commentController;

	@MockBean
	protected CommentService commentService;
	
	@MockBean
	private ActivityService activityService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	protected ItineraryService itineraryService;
	
	@MockBean
	protected LandmarkService landmarkService;
	
	@MockBean
	protected ImageService imageService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		
		
		// ROLES
		
		Role ro1 = new Role();
		
		ro1.setId((long)1);
		ro1.setRoleType(RoleType.ROLE_USER);
		
		Set<Role> roles = new HashSet<>();
		roles.add(ro1);
		
		
		// USUARIOS
		
		User us1 = new User();
		
		us1.setId((long)1);
		us1.setEmail("testuser@email.com");
		us1.setFirstName("Name 1");
		us1.setLastName("Surname 1");
		us1.setPassword("user1");
		us1.setUsername("user1");
		us1.setPlan(0);
		
		User us2 = new User();
		
		us2.setEmail("user2@email.com");
		us2.setExpirationDate(null);
		us2.setFirstName("Firstname2");
		us2.setId((long)2);
		us2.setLastName("Lastname2");
		us2.setPassword("password2");
		us2.setPlan(0);
		us2.setRoles(roles);
		us2.setUsername("user2");
		
		
		// ITINERARIOS
		
	    Itinerary it1 = new Itinerary();
	    
	    it1.setId((long)1);
	    it1.setName("itinerary test 1");
	    it1.setDescription("lorem ipsum 1");
	    it1.setStatus(StatusType.PUBLISHED);
	    it1.setBudget(10.);
	    it1.setEstimatedDays(2);
	    it1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it1.setViews(0);
	    it1.setAuthor(us1);
	    
		it1.toString();
		it1.hashCode();

	    doReturn(it1).when(this.itineraryService).save(any());
	    
	  //Comments
	  	Comment c1 = new Comment();
	  		
	  	c1.setId(TEST_COMMENT_ID_1);
	  	c1.setContent("Comentario de prueba");
	  	c1.setRating(4);
		c1.setItinerary(it1);
		c1.setAuthor(us1);
	    
	    
		//IMAGE
		
		Image img = new Image();
		
		
		// PAGEABLE
		
		Optional<User> usuario = Optional.of(us1);
		Optional<Image> imagen = Optional.of(img);
		
		given(this.commentService.findById((long) TEST_COMMENT_ID_1)).willReturn(Optional.of(c1));
		given(this.commentService.findById((long) TEST_COMMENT_ID_NOT_FOUND)).willReturn(Optional.empty());
	    given(this.itineraryService.findById((long) TEST_ITINERARY_ID_1)).willReturn(Optional.of(it1));
	    given(this.itineraryService.findById((long) TEST_ITINERARY_ID_NOT_FOUND)).willReturn(Optional.empty());
	    given(this.userService.getCurrentUsername()).willReturn(us1.getUsername());
	    given(this.userService.getByUsername(us1.getUsername())).willReturn(usuario);
	    given(this.imageService.findById(78)).willReturn(imagen);
	    given(this.commentService.save(any())).willReturn(c1);
	    
	}
	

	@Test
	void testCreateComment() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("content", "Comentario de prueba");
		activityJSON.put("rating", 4);
		activityJSON.put("itinerary", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(post("/comment/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isOk())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(TEST_COMMENT_ID_1)))
        .andExpect(jsonPath("$.content", is("Comentario de prueba")))
        .andExpect(jsonPath("$.rating", is(4)));
	}
	
	@Test
	void testCreateCommentNotRegistered() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("anonymousUser");
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("content", "Comentario de prueba");
		activityJSON.put("rating", 4);
		activityJSON.put("itinerary", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(post("/comment/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("El usuario no tiene permiso de comentar un itinerario sin registrarse")));
	}
	
	@Test
	void testCreateCommentNotRegistered2() throws Exception {
		given(this.userService.getByUsername(any())).willReturn(Optional.empty());
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("content", "Comentario de prueba");
		activityJSON.put("rating", 4);
		activityJSON.put("itinerary", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(post("/comment/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("El usuario actual no existe")));
	}
	
	@Test
	void testCreateCommentItineraryNotFound() throws Exception {
		given(this.userService.getByUsername(any())).willReturn(Optional.empty());
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("content", "Comentario de prueba");
		activityJSON.put("rating", 4);
		activityJSON.put("itinerary", TEST_ITINERARY_ID_NOT_FOUND);
		
		this.mockMvc.perform(post("/comment/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("El itinerario que se quiere comentar no existe")));
	}
	
	@Test
	void testDeleteComment() throws Exception {
		
		this.mockMvc.perform(delete("/comment/delete/{id}", TEST_COMMENT_ID_1))
		
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("Comentario eliminado correctamente")));
	}
	
	@Test
	void testDeleteCommentNotFound() throws Exception {
		
		this.mockMvc.perform(delete("/comment/delete/{id}", TEST_COMMENT_ID_NOT_FOUND))
		
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("No existe el comentario indicado")));
	}
	
	@Test
	void testDeleteCommentNotRegistered() throws Exception {
		
		given(this.userService.getCurrentUsername()).willReturn("anonymousUser");
		
		this.mockMvc.perform(delete("/comment/delete/{id}", TEST_COMMENT_ID_1))
		
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("No puede eliminar un comentario de un itinerario del que no es creador")));
	}
	
//	@Test
//	void testCreateItineraryNotRegistered() throws Exception {
//		
//		given(this.userService.getCurrentUsername()).willReturn("Unregistered");
//		
//		JSONObject activityJSON = new JSONObject();
//		
//		activityJSON.put("budget", 0);
//		activityJSON.put("description", "lorem ipsum 1");
//		activityJSON.put("estimatedDays", 1);
//		activityJSON.put("name", "itinerary test 1");
//		activityJSON.put("recomendedSeason", "WINTER");
//		activityJSON.put("status", "DRAFT");
//		
//		this.mockMvc.perform(post("/itinerary/create")
//		.contentType(MediaType.APPLICATION_JSON)
//		.content(activityJSON.toString()))
//
//		// Validate the response code and content type
//		.andExpect(status().is4xxClientError())
//
//        .andExpect(jsonPath("$.text", is("El usuario debe estar registrado para publicar un itinerario.")));
//	}
//	
//	@Test
//	@WithMockUser(username = "user1", password = "user1")
//	void testDeleteItinerary() throws Exception {
//		
//		this.mockMvc.perform(delete("/itinerary/delete/{id}", TEST_ITINERARY_ID_1))
//		
//		// Validate the response code and content type
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//        
//
////		// Validate the returned fields
//		.andExpect(jsonPath("$.text", is("Itinerario eliminado correctamente")));
//	}
//	
//	@Test
//	@WithMockUser(username = "user1", password = "user1")
//	void testDeleteItineraryNotFound() throws Exception {
//		
//		this.mockMvc.perform(delete("/itinerary/delete/{id}", TEST_ITINERARY_ID_NOT_FOUND))
//		
//		// Validate the response code and content type
//		.andExpect(status().is4xxClientError())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//        
//
////		// Validate the returned fields
//		.andExpect(jsonPath("$.text", is("No existe el itinerario indicado")));
//	}
//	
//	@Test
//	void testDeleteItineraryNotRegistered() throws Exception {
//		given(this.userService.getCurrentUsername()).willReturn("Unregistered");
//		
//		this.mockMvc.perform(delete("/itinerary/delete/{id}", TEST_ITINERARY_ID_1))
//		
//		// Validate the response code and content type
//		.andExpect(status().is4xxClientError())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//        
//
////		// Validate the returned fields
//		.andExpect(jsonPath("$.text", is("No puede borrar un itinerario que no es suyo")));
//	}
	
}