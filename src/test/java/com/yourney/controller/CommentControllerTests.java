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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.yourney.model.Comment;
import com.yourney.model.Image;
import com.yourney.model.Itinerary;
import com.yourney.model.StatusType;
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
	private static final int TEST_ITINERARY_ID_2 = 2;
	private static final int TEST_ITINERARY_ID_NOT_FOUND = 10;
	
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
		Role ro2 = new Role();
		
		ro1.setId((long)1);
		ro1.setRoleType(RoleType.ROLE_USER);
		
		ro2.setId((long)2);
		ro2.setRoleType(RoleType.ROLE_ADMIN);
		
		Set<Role> roles = new HashSet<>();
		roles.add(ro1);

		Set<Role> rolesAdmin = new HashSet<>(Set.of(ro1, ro2));
		
		
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
		
		User us3 = new User();
		
		us3.setEmail("user3email.com");
		us3.setExpirationDate(null);
		us3.setFirstName("Firstname3");
		us3.setId((long)3);
		us3.setLastName("Lastname3");
		us3.setPassword("password3");
		us3.setPlan(0);
		us3.setRoles(roles);
		us3.setUsername("user3");

		User admin = new User();
		admin.setEmail("admin@email.com");
		admin.setExpirationDate(null);
		admin.setFirstName("admin");
		admin.setId((long)4);
		admin.setLastName("admin");
		admin.setPassword("admin");
		admin.setPlan(0);
		admin.setRoles(rolesAdmin);
		admin.setUsername("admin");
		
		
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
	    
	    Itinerary it2 = new Itinerary();
	    
	    it2.setId((long)1);
	    it2.setName("itinerary test 1");
	    it2.setDescription("lorem ipsum 1");
	    it2.setStatus(StatusType.DRAFT);
	    it2.setBudget(10.);
	    it2.setEstimatedDays(2);
	    it2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it2.setViews(0);
	    it2.setAuthor(us1);
	    
	    it2.toString();
	    it2.hashCode();
	    
	    //Comments
	  	Comment c1 = new Comment();
	  		
	  	c1.setId(TEST_COMMENT_ID_1);
	  	c1.setContent("Comentario de prueba");
	  	c1.setRating(4);
		c1.setItinerary(it1);
		c1.setAuthor(us2);
		
		List<Comment> comments = new ArrayList<>();
		comments.add(c1);
		it1.setComments(comments);
		
	    
	    
		//IMAGE
		
		Image img = new Image();
		
		
		// PAGEABLE
		
		Optional<User> usuario1 = Optional.of(us1);
		Optional<User> usuario2 = Optional.of(us2);
		Optional<User> usuario3 = Optional.of(us3);
		Optional<User> adminUser = Optional.of(admin);
		Optional<Image> imagen = Optional.of(img);
		
		given(this.commentService.findById((long) TEST_COMMENT_ID_1)).willReturn(Optional.of(c1));
		given(this.commentService.findById((long) TEST_COMMENT_ID_NOT_FOUND)).willReturn(Optional.empty());
	    given(this.itineraryService.findById((long) TEST_ITINERARY_ID_1)).willReturn(Optional.of(it1));
	    given(this.itineraryService.findById((long) TEST_ITINERARY_ID_2)).willReturn(Optional.of(it2));
	    given(this.itineraryService.findById((long) TEST_ITINERARY_ID_NOT_FOUND)).willReturn(Optional.empty());
	    given(this.userService.getCurrentUsername()).willReturn(us1.getUsername());
	    given(this.userService.getByUsername(us1.getUsername())).willReturn(usuario1);
	    given(this.userService.getByUsername(us2.getUsername())).willReturn(usuario2);
	    given(this.userService.getByUsername(us3.getUsername())).willReturn(usuario3);
		given(this.userService.getByUsername(admin.getUsername())).willReturn(adminUser);
	    given(this.imageService.findById(78)).willReturn(imagen);
	    given(this.commentService.save(any())).willReturn(c1);
	    
	}
	

	@Test
	void testCreateComment() throws Exception {
		
		given(this.userService.getCurrentUsername()).willReturn("user3");
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("content", "Comentario de prueba");
		activityJSON.put("rating", 4);
		activityJSON.put("itinerary", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(post("/comment/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isOk())
        

		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(TEST_COMMENT_ID_1)))
        .andExpect(jsonPath("$.content", is("Comentario de prueba")))
        .andExpect(jsonPath("$.rating", is(4)));
	}
	
	@Test
	void testCreateCommentInOwnItinerary() throws Exception {
		
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("content", "Comentario de prueba");
		activityJSON.put("rating", 4);
		activityJSON.put("itinerary", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(post("/comment/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isForbidden())
        

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("Un usuario no puede comentar sus propios itinerarios")));
	}
	
	@Test
	void testCreateCommentAlreadyCommented() throws Exception {
		
		given(this.userService.getCurrentUsername()).willReturn("user2");
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("content", "Comentario de prueba");
		activityJSON.put("rating", 4);
		activityJSON.put("itinerary", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(post("/comment/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isForbidden())
        

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El usuario ya ha realizado un comentario en este itinerario")));
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
		given(this.userService.getCurrentUsername()).willReturn("anonymousUser2");
		given(this.userService.getByUsername("anonymousUser2")).willReturn(Optional.empty());
		
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
		given(this.userService.getCurrentUsername()).willReturn("user3");
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
	void testCreateCommentItineraryDraft() throws Exception {
		
		given(this.userService.getCurrentUsername()).willReturn("user2");

		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("content", "Comentario de prueba");
		activityJSON.put("rating", 4);
		activityJSON.put("itinerary", TEST_ITINERARY_ID_2);
		
		this.mockMvc.perform(post("/comment/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
        

//		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("No tiene permisos para comentar este itinerario")));
	}
	
	@Test
	void testDeleteComment() throws Exception {
		
		given(this.userService.getCurrentUsername()).willReturn("user2");
		
		this.mockMvc.perform(delete("/comment/delete/{id}", TEST_COMMENT_ID_1))
		
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("Comentario eliminado correctamente")));
	}

	@Test
	void testAdminDeleteComment() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("admin");
		this.mockMvc.perform(delete("/comment/delete/{id}", TEST_COMMENT_ID_1))
		
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("Comentario eliminado correctamente")));
	}
	
	@Test
	void testDeleteCommentNotFound() throws Exception {
		
		given(this.userService.getCurrentUsername()).willReturn("user2");
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
}