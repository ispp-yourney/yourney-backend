package com.yourney.controller;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
class ItineraryControllerTests {

	private static final int TEST_ITINERARY_ID_1 = 1;
	private static final int TEST_ITINERARY_ID_2 = 2;
	private static final int TEST_ITINERARY_ID_4 = 4;
	private static final int TEST_ITINERARY_ID_NOT_FOUND = 10;
	private static final int TEST_ITINERARY_PAGE = 1;
	private static final int TEST_ITINERARY_PAGE_2 = 0;
	private static final int TEST_ITINERARY_SIZE = 10;
	private static final String TEST_ITINERARY_COUNTRY_1 = "Brazil";
	private static final String TEST_ITINERARY_CITY_1 = "Rio de Janeiro";
	private static final String TEST_ITINERARY_COUNTRY_2 = "Francia";
	private static final String TEST_ITINERARY_CITY_2 = "ParÃ­s";
	private static final String TEST_ITINERARY_COUNTRY_3 = "";
	private static final String TEST_ITINERARY_CITY_3 = "";
	private static final Double TEST_ITINERARY_MAXBUDGET = 9000.;
	private static final Double TEST_ITINERARY_LATITUDE = 60.0;
	private static final Double TEST_ITINERARY_LONGITUDE = 60.0;
	private static final Integer TEST_ITINERARY_MAXDAYS = 1000;
	private static final String TEST_ITINERARY_NAME = "itinerary test 1";
	private static final int TEST_ITINERARY_USER_ID = 1;
	private static final String TEST_ITINERARY_USERNAME = "user1";
	private static final int TEST_COMMENT_ID = 1;
	private static final int TEST_COMMENT_ID_2 = 2;
	private static final int TEST_COMMENT_ID_3 = 3;
	private static final String NOT_ALLOWED = "El usuario no tiene permiso para ver esta consulta";
	private static final String ITINERARY_DELETED = "Itinerario eliminado correctamente";
	private static final String ITINERARY_NOT_FOUND = "No existe el itinerario indicado";
	

	
	@Autowired
	protected ItineraryController itineraryController;

	@MockBean
	private ActivityService activityService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	protected ItineraryService itineraryService;
	
	@MockBean
	protected LandmarkService landmarkService;
	
	@MockBean
	protected CommentService commentService;
	
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

		Role ro2 = new Role();
		ro2.setId((long)2);
		ro2.setRoleType(RoleType.ROLE_ADMIN);
		Set<Role> rolesAdmin = new HashSet<>();
		rolesAdmin.add(ro1);
		rolesAdmin.add(ro2);
		
		
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
		us2.setPassword("user2");
		us2.setPlan(0);
		us2.setRoles(roles);
		us2.setUsername("user2");
		
		User admin = new User();
		admin.setEmail("admin@email.com");
		admin.setExpirationDate(null);
		admin.setFirstName("Firstname2");
		admin.setId((long)3);
		admin.setLastName("Lastname2");
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
	    
	    it2.setId((long)2);
	    it2.setName("itinerary test 2");
	    it2.setDescription("lorem ipsum 2");
	    it2.setStatus(StatusType.PUBLISHED);
	    it2.setBudget(100.);
	    it2.setEstimatedDays(3);
	    it2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it2.setViews(50);
	    it2.setAuthor(us2);
	    
	    Itinerary it3 = new Itinerary();
	    
	    it3.setId((long)3);
	    it3.setName("itinerary test 3");
	    it3.setDescription("lorem ipsum 3");
	    it3.setStatus(StatusType.PUBLISHED);
	    it3.setBudget(100.);
	    it3.setEstimatedDays(4);
	    it3.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it3.setViews(15);
	    it3.setAuthor(us1);
	    
	    Itinerary it4 = new Itinerary();
	    
	    it4.setId((long)4);
	    it4.setName("itinerary test 4");
	    it4.setDescription("lorem ipsum 4");
	    it4.setStatus(StatusType.DRAFT);
	    it4.setBudget(100.);
	    it4.setEstimatedDays(4);
	    it4.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it4.setViews(15);
	    it4.setAuthor(us2);
	    
	    
	    Itinerary it1Actualizado = new Itinerary();
	    
	    it1Actualizado.setId((long)1);
	    it1Actualizado.setName("itinerary test 1 actualizado");
	    it1Actualizado.setDescription("lorem ipsum 1 actualizado");
	    it1Actualizado.setStatus(StatusType.PUBLISHED);
	    it1Actualizado.setBudget(10.);
	    it1Actualizado.setEstimatedDays(2);
	    it1Actualizado.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it1Actualizado.setViews(0);
	    it1Actualizado.setAuthor(us1);
	    
	    // COMMENTS
	    
	  	Comment c1 = new Comment();
	  		
	  	c1.setId(TEST_COMMENT_ID);
	  	c1.setContent("Comentario de prueba");
	  	c1.setRating(4);
		c1.setItinerary(it1);
		c1.setAuthor(us1);
		
		Comment c2 = new Comment();
  		
	  	c2.setId(TEST_COMMENT_ID_2);
	  	c2.setContent("Comentario de prueba 2");
	  	c2.setRating(3);
		c2.setItinerary(it2);
		c2.setAuthor(us1);
	    
		Comment c3 = new Comment();
  		
	  	c3.setId(TEST_COMMENT_ID_3);
	  	c3.setContent("Comentario de prueba 3");
	  	c3.setRating(4);
		c3.setItinerary(it1);
		c3.setAuthor(us1);
	    
		Collection<Comment> comments = new ArrayList<>();
		comments.add(c1);
		comments.add(c3);
		
		Collection<Comment> comments2 = new ArrayList<>();
		comments.add(c2);
		
		it1.setComments(comments);
		it2.setComments(comments2);
		
	    // LADNMARKS
	    
	    Landmark la1 = new Landmark();
	    
	    la1.setCategory("category 1");
	    la1.setCity(TEST_ITINERARY_CITY_1);
	    la1.setCountry(TEST_ITINERARY_COUNTRY_1);
	    la1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    la1.setDescription("lorem ipsum 1");
	    la1.setEmail("monumento1@email.com");
	    la1.setId((long) 1);
	    la1.setImage(null);
	    la1.setImageUrl(null);
	    la1.setInstagram(null);
	    la1.setLatitude(60.0);
	    la1.setLongitude(60.0);
	    la1.setName("Monumento 1");
	    la1.setPhone("+1 111111111");
	    la1.setPrice(0.0);
	    la1.setEndPromotionDate(LocalDateTime.of(2050, 10, 10, 10, 10, 10));
	    la1.setTwitter(null);
	    la1.setViews((long) 10);
	    la1.setWebsite(null);

		la1.hashCode();
		la1.toString();
	    
	    Landmark la2 = new Landmark();
	    
	    la2.setCategory("category 2");
	    la2.setCity(TEST_ITINERARY_CITY_1);
	    la2.setCountry(TEST_ITINERARY_COUNTRY_1);
	    la2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    la2.setDescription("lorem ipsum 2");
	    la2.setEmail("monumento2@email.com");
	    la2.setId((long) 2);
	    la2.setImage(null);
	    la2.setImageUrl(null);
	    la2.setInstagram(null);
	    la2.setLatitude(50.0);
	    la2.setLongitude(50.0);
	    la2.setName("Monumento 2");
	    la2.setPhone("+1 111111111");
	    la2.setPrice(0.0);
	    la2.setEndPromotionDate(LocalDateTime.of(2050, 10, 10, 10, 10, 10));
	    la2.setTwitter(null);
	    la2.setViews((long) 10);
	    la2.setWebsite(null);
	    
	    Landmark la3 = new Landmark();
	    
	    la3.setCategory("category 3");
	    la3.setCity(TEST_ITINERARY_CITY_1);
	    la3.setCountry(TEST_ITINERARY_COUNTRY_1);
	    la3.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    la3.setDescription("lorem ipsum 3");
	    la3.setEmail("monumento3@email.com");
	    la3.setId((long) 3);
	    la3.setImage(null);
	    la3.setImageUrl(null);
	    la3.setInstagram(null);
	    la3.setLatitude(40.0);
	    la3.setLongitude(40.0);
	    la3.setName("Monumento 3");
	    la3.setPhone("+1 111111111");
	    la3.setPrice(0.0);
	    la3.setEndPromotionDate(LocalDateTime.of(2050, 10, 10, 10, 10, 10));
	    la3.setTwitter(null);
	    la3.setViews((long) 10);
	    la3.setWebsite(null);
	    
	    Landmark la4 = new Landmark();
	    
	    la4.setCategory("category 4");
	    la4.setCity(TEST_ITINERARY_CITY_2);
	    la4.setCountry(TEST_ITINERARY_COUNTRY_2);
	    la4.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    la4.setDescription("lorem ipsum 4");
	    la4.setEmail("monumento4@email.com");
	    la4.setId((long) 3);
	    la4.setImage(null);
	    la4.setImageUrl(null);
	    la4.setInstagram(null);
	    la4.setLatitude(30.0);
	    la4.setLongitude(30.0);
	    la4.setName("Monumento 4");
	    la4.setPhone("+1 111111111");
	    la4.setPrice(0.0);
	    la4.setEndPromotionDate(LocalDateTime.of(2050, 10, 10, 10, 10, 10));
	    la4.setTwitter(null);
	    la4.setViews((long) 10);
	    la4.setWebsite(null);

	    
	    // ACTIVIDADES
	    
	    Activity ac1 = new Activity(); 

		ac1.setId(1);
		ac1.setTitle("activity test 1");
		ac1.setDescription("lorem ipsum 1");
		ac1.setDay(1);
		ac1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		ac1.setItinerary(it1);
		ac1.setLandmark(la1);
		
		Activity ac2 = new Activity(); 

		ac2.setId(2);
		ac2.setTitle("activity test 2");
		ac2.setDescription("lorem ipsum 2");
		ac2.setDay(1);
		ac2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		ac2.setItinerary(it1);
		ac2.setLandmark(la2);
		
		Activity ac3 = new Activity(); 

		ac3.setId(3);
		ac3.setTitle("activity test 3");
		ac3.setDescription("lorem ipsum 3");
		ac3.setDay(1);
		ac3.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		ac3.setItinerary(it2);
		ac3.setLandmark(la3);
		
		Activity ac4 = new Activity(); 

		ac4.setId(4);
		ac4.setTitle("activity test 4");
		ac4.setDescription("lorem ipsum 4");
		ac4.setDay(1);
		ac4.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		ac4.setItinerary(it2);
		ac4.setLandmark(la1);
		
		Activity ac5 = new Activity(); 

		ac5.setId(4);
		ac5.setTitle("activity test 5");
		ac5.setDescription("lorem ipsum 5");
		ac5.setDay(1);
		ac5.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		ac5.setItinerary(it3);
		ac5.setLandmark(la4);
		
		ac5.toString();
		ac5.hashCode();
		
		//IMAGE
		
		Image img = new Image();
		
		
		// PAGEABLE
		Pageable pageable = PageRequest.of(TEST_ITINERARY_PAGE, TEST_ITINERARY_SIZE);
		Pageable pageable2 = PageRequest.of(TEST_ITINERARY_PAGE_2, TEST_ITINERARY_SIZE);
		ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
		
		List<ItineraryProjection> itineraries1 = new ArrayList<>();
		ItineraryProjection itp1 = factory.createProjection(ItineraryProjection.class, it1);
		ItineraryProjection itp2 = factory.createProjection(ItineraryProjection.class, it2);
		itineraries1.add(itp1);
		itineraries1.add(itp2);
		Page<ItineraryProjection> itinerariesPage1 = new PageImpl<>(itineraries1);
		
		List<ItineraryProjection> itineraries2 = new ArrayList<>();
		itp1 = factory.createProjection(ItineraryProjection.class, it1);
		itp2 = factory.createProjection(ItineraryProjection.class, it2);
		ItineraryProjection itp3 = factory.createProjection(ItineraryProjection.class, it3);
		itineraries2.add(itp1);
		itineraries2.add(itp2);
		itineraries2.add(itp3);
		
		Page<ItineraryProjection> itinerariesPage2 = new PageImpl<>(itineraries2);
		
		List<ItineraryProjection> itineraries3 = new ArrayList<>();
		
		itineraries3.add(itp1);
		Page<ItineraryProjection> itinerariesPage3 = new PageImpl<>(itineraries3);
		
		List<ItineraryProjection> itineraries4 = new ArrayList<>();
		itineraries4.add(itp1);
		itineraries4.add(itp3);
		Page<ItineraryProjection> itinerariesPage4 = new PageImpl<>(itineraries4);
		
		List<ItineraryProjection> itineraries5 = new ArrayList<>();
		itineraries5.add(itp2);
		
		Page<ItineraryProjection> itinerariesPage5 = new PageImpl<>(itineraries5);
		
		List<ItineraryProjection> itineraries6 = new ArrayList<>();
		
		ItineraryProjection itp4 = factory.createProjection(ItineraryProjection.class, it4);
		itineraries6.add(itp2);
		itineraries6.add(itp3);
		itineraries6.add(itp4);
		itineraries6.add(itp1);
		
		Page<ItineraryProjection> itinerariesPage6 = new PageImpl<>(itineraries6);
		
		List<Activity> activities = new ArrayList<>();
		activities.add(ac1);
		activities.add(ac2);
		
		List<Activity> activities2 = new ArrayList<>();
		activities2.add(ac3);
		activities2.add(ac4);
		
		it1.setActivities(activities);
		it2.setActivities(activities2);
		
		Optional<User> usuario1 = Optional.of(us1);
		Optional<User> adminUser = Optional.of(admin);
		Optional<Image> imagen = Optional.of(img);
		
	    given(this.itineraryService.findById((long) TEST_ITINERARY_ID_1)).willReturn(Optional.of(it1));
	    given(this.itineraryService.findById((long) TEST_ITINERARY_ID_2)).willReturn(Optional.of(it2));
	    given(this.itineraryService.findById((long) TEST_ITINERARY_ID_4)).willReturn(Optional.of(it4));
	    given(this.itineraryService.findById((long) TEST_ITINERARY_ID_NOT_FOUND)).willReturn(Optional.empty());
	    given(this.userService.getCurrentUsername()).willReturn(us1.getUsername());
		given(this.userService.getByUsername(us1.getUsername())).willReturn(Optional.of(us1));
		given(this.userService.getByUsername(us2.getUsername())).willReturn(Optional.of(us2));
		given(this.userService.getByUsername(admin.getUsername())).willReturn(Optional.of(admin));
	    given(this.itineraryService.searchByProperties("%" + TEST_ITINERARY_COUNTRY_1 + "%", "%" + TEST_ITINERARY_CITY_1 + "%", TEST_ITINERARY_MAXBUDGET, TEST_ITINERARY_MAXDAYS, pageable)).willReturn(itinerariesPage1);
	    given(this.itineraryService.searchByProperties("%" + TEST_ITINERARY_COUNTRY_1 + "%", "%" + TEST_ITINERARY_CITY_1 + "%", 1000000000., 1000000000, pageable)).willReturn(itinerariesPage1);
	    given(this.itineraryService.searchByDistance(TEST_ITINERARY_LATITUDE, TEST_ITINERARY_LONGITUDE, pageable)).willReturn(itinerariesPage2);

	    given(this.commentService.findById((long) TEST_COMMENT_ID)).willReturn(Optional.of(c1));
	    given(this.commentService.findById((long) TEST_COMMENT_ID_2)).willReturn(Optional.of(c2));
	    given(this.commentService.findById((long) TEST_COMMENT_ID_3)).willReturn(Optional.of(c3));
	    given(this.itineraryService.searchOrderedByComments("%" + TEST_ITINERARY_COUNTRY_3 + "%", "%" + TEST_ITINERARY_CITY_3 + "%", pageable2)).willReturn(itinerariesPage1);
	    given(this.itineraryService.searchOrderedByCommentsLastMonth("%" + TEST_ITINERARY_COUNTRY_3 + "%", "%" + TEST_ITINERARY_CITY_3 + "%", pageable2)).willReturn(itinerariesPage1);
	    given(this.itineraryService.searchOrderedByRating("%" + TEST_ITINERARY_COUNTRY_3 + "%", "%" + TEST_ITINERARY_CITY_3 + "%", pageable2)).willReturn(itinerariesPage1);
	    given(this.itineraryService.searchOrderedByRatingLastMonth("%" + TEST_ITINERARY_COUNTRY_3 + "%", "%" + TEST_ITINERARY_CITY_3 + "%", pageable2)).willReturn(itinerariesPage1);
	    given(this.itineraryService.searchOrderedByViews("%" + TEST_ITINERARY_COUNTRY_3 + "%", "%" + TEST_ITINERARY_CITY_3 + "%", pageable2)).willReturn(itinerariesPage6);
	    
	    given(this.itineraryService.searchByName(pageable, "%"+TEST_ITINERARY_NAME+"%")).willReturn(itinerariesPage3);
	    given(this.itineraryService.searchByUserId(pageable, (long) TEST_ITINERARY_USER_ID)).willReturn(itinerariesPage4);
	    given(this.itineraryService.searchByUsername(pageable, TEST_ITINERARY_USERNAME)).willReturn(itinerariesPage4);
	    given(this.itineraryService.searchByUsername(pageable, "user2")).willReturn(itinerariesPage5);

	    given(this.userService.getByUsername(us1.getUsername())).willReturn(usuario1);
		given(this.userService.getByUsername(admin.getUsername())).willReturn(adminUser);
	    given(this.itineraryService.searchByUserId(pageable, us1.getId())).willReturn(itinerariesPage4);
	    given(this.itineraryService.searchByCurrentUsername(pageable, us1.getUsername())).willReturn(itinerariesPage4);
	    given(this.imageService.findById(78)).willReturn(imagen);
	    
	    given(this.itineraryService.save(it1Actualizado)).willReturn(it1Actualizado);
	    
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testShowItinerary() throws Exception {
		this.mockMvc.perform(get("/itinerary/show/{id}", TEST_ITINERARY_ID_1))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("itinerary test 1")))
        .andExpect(jsonPath("$.description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.status", is("PUBLISHED")))
        .andExpect(jsonPath("$.budget", is(10.)))
        .andExpect(jsonPath("$.estimatedDays", is(2)))
        .andExpect(jsonPath("$.views", is(0)));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testShowItineraryNotAuthor() throws Exception {
		this.mockMvc.perform(get("/itinerary/show/{id}", TEST_ITINERARY_ID_2))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(2)))
        .andExpect(jsonPath("$.name", is("itinerary test 2")))
        .andExpect(jsonPath("$.description", is("lorem ipsum 2")))
        .andExpect(jsonPath("$.status", is("PUBLISHED")))
        .andExpect(jsonPath("$.budget", is(100.)))
        .andExpect(jsonPath("$.estimatedDays", is(3)))
        .andExpect(jsonPath("$.views", is(51)));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testShowItineraryStatusDraft() throws Exception {
		this.mockMvc.perform(get("/itinerary/show/{id}", TEST_ITINERARY_ID_4))
		// Validate the response code and content type
		.andExpect(status().isForbidden())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El itinerario solicitado no ha sido publicado por su autor.")));
	}
	
	@Test
	void testShowItineraryNotFound() throws Exception {
		this.mockMvc.perform(get("/itinerary/show/{id}", TEST_ITINERARY_ID_NOT_FOUND))
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is(ITINERARY_NOT_FOUND)));
	}
	
	@Test
	void testSearchOrderedByComments() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("admin");
		
		this.mockMvc.perform(get("/itinerary/searchOrderedByComments"))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("itinerary test 1")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.content[0].budget", is(10.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[0].views", is(0)))
        .andExpect(jsonPath("$.content[1].id", is(2)))
        .andExpect(jsonPath("$.content[1].name", is("itinerary test 2")))
        .andExpect(jsonPath("$.content[1].description", is("lorem ipsum 2")))
        .andExpect(jsonPath("$.content[1].budget", is(100.)))
        .andExpect(jsonPath("$.content[1].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[1].views", is(50)));
	}
	
	@Test
	void testSearchOrderedByCommentsNotAllowed() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user2");
		
		this.mockMvc.perform(get("/itinerary/searchOrderedByComments"))
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		
        .andExpect(jsonPath("$.text", is(NOT_ALLOWED)));
	}
	
	@Test
	void testSearchOrderedByCommentsLastMonth() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("admin");
		
		this.mockMvc.perform(get("/itinerary/searchOrderedByComments/lastMonth"))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("itinerary test 1")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.content[0].budget", is(10.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[0].views", is(0)))
        .andExpect(jsonPath("$.content[1].id", is(2)))
        .andExpect(jsonPath("$.content[1].name", is("itinerary test 2")))
        .andExpect(jsonPath("$.content[1].description", is("lorem ipsum 2")))
        .andExpect(jsonPath("$.content[1].budget", is(100.)))
        .andExpect(jsonPath("$.content[1].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[1].views", is(50)));
	}
	
	@Test
	void testSearchOrderedByCommentsLastMonthNotAllowed() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user2");
		
		this.mockMvc.perform(get("/itinerary/searchOrderedByComments/lastMonth"))
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		
        .andExpect(jsonPath("$.text", is(NOT_ALLOWED)));
	}
	
	@Test
	void testSearchOrderedByRating() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("admin");
		
		this.mockMvc.perform(get("/itinerary/searchOrderedByRating"))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("itinerary test 1")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.content[0].budget", is(10.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[0].views", is(0)))
        .andExpect(jsonPath("$.content[1].id", is(2)))
        .andExpect(jsonPath("$.content[1].name", is("itinerary test 2")))
        .andExpect(jsonPath("$.content[1].description", is("lorem ipsum 2")))
        .andExpect(jsonPath("$.content[1].budget", is(100.)))
        .andExpect(jsonPath("$.content[1].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[1].views", is(50)));
	}
	
	@Test
	void testSearchOrderedByRatingNotAllowed() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user2");
		
		this.mockMvc.perform(get("/itinerary/searchOrderedByRating"))
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		
        .andExpect(jsonPath("$.text", is(NOT_ALLOWED)));
	}
		
	@Test
	void testSearchOrderedByRatingLastMonth() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("admin");
		
		this.mockMvc.perform(get("/itinerary/searchOrderedByRating/lastMonth"))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("itinerary test 1")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.content[0].budget", is(10.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[0].views", is(0)))
        .andExpect(jsonPath("$.content[1].id", is(2)))
        .andExpect(jsonPath("$.content[1].name", is("itinerary test 2")))
        .andExpect(jsonPath("$.content[1].description", is("lorem ipsum 2")))
        .andExpect(jsonPath("$.content[1].budget", is(100.)))
        .andExpect(jsonPath("$.content[1].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[1].views", is(50)));
	}	
	
	@Test
	void testSearchOrderedByRatingLastMonthLastMonth() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user2");
		
		this.mockMvc.perform(get("/itinerary/searchOrderedByRating/lastMonth"))
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		
        .andExpect(jsonPath("$.text", is(NOT_ALLOWED)));
	}
	
	@Test
	void testSearchOrderedByViews() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("admin");
		
		this.mockMvc.perform(get("/itinerary/searchOrderedByViews"))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.content", hasSize(4)))
		.andExpect(jsonPath("$.content[0].id", is(2)))
        .andExpect(jsonPath("$.content[0].name", is("itinerary test 2")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum 2")))
        .andExpect(jsonPath("$.content[0].budget", is(100.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[0].views", is(50)))
        .andExpect(jsonPath("$.content[1].id", is(3)))
        .andExpect(jsonPath("$.content[1].name", is("itinerary test 3")))
        .andExpect(jsonPath("$.content[1].description", is("lorem ipsum 3")))
        .andExpect(jsonPath("$.content[1].budget", is(100.)))
        .andExpect(jsonPath("$.content[1].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[1].views", is(15)))
        .andExpect(jsonPath("$.content[2].id", is(4)))
        .andExpect(jsonPath("$.content[2].name", is("itinerary test 4")))
        .andExpect(jsonPath("$.content[2].description", is("lorem ipsum 4")))
        .andExpect(jsonPath("$.content[2].budget", is(100.)))
        .andExpect(jsonPath("$.content[2].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[2].views", is(15)))
        .andExpect(jsonPath("$.content[3].id", is(1)))
        .andExpect(jsonPath("$.content[3].name", is("itinerary test 1")))
        .andExpect(jsonPath("$.content[3].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.content[3].budget", is(10.)))
        .andExpect(jsonPath("$.content[3].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[3].views", is(0)));
        
	}	
	
	@Test
	void testSearchOrderedByViewsNotAllowed() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user2");
		
		this.mockMvc.perform(get("/itinerary/searchOrderedByViews"))
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		
        .andExpect(jsonPath("$.text", is("El usuario no tiene permiso para realizar esta consulta.")));
	}
	
	@Test
	void testSearchByProperties() throws Exception {
		this.mockMvc.perform(get("/itinerary/search?page={page}&size={size}&country={country}&city={city}", TEST_ITINERARY_PAGE, TEST_ITINERARY_SIZE, TEST_ITINERARY_COUNTRY_1, TEST_ITINERARY_CITY_1))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("itinerary test 1")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.content[0].budget", is(10.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[0].views", is(0)))
        .andExpect(jsonPath("$.content[1].id", is(2)))
        .andExpect(jsonPath("$.content[1].name", is("itinerary test 2")))
        .andExpect(jsonPath("$.content[1].description", is("lorem ipsum 2")))
        .andExpect(jsonPath("$.content[1].budget", is(100.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[1].views", is(50)));
	}
	
	@Test
	void testSearchItinerariesByDistance() throws Exception {
		this.mockMvc.perform(get("/itinerary/searchByDistance?page={page}&size={size}&latitude={latitude}&longitude={longitude}", TEST_ITINERARY_PAGE, TEST_ITINERARY_SIZE, TEST_ITINERARY_LATITUDE, TEST_ITINERARY_LONGITUDE))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.content", hasSize(3)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("itinerary test 1")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.content[0].budget", is(10.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[0].views", is(0)))
        .andExpect(jsonPath("$.content[1].id", is(2)))
        .andExpect(jsonPath("$.content[1].name", is("itinerary test 2")))
        .andExpect(jsonPath("$.content[1].description", is("lorem ipsum 2")))
        .andExpect(jsonPath("$.content[1].budget", is(100.)))
        .andExpect(jsonPath("$.content[1].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[1].views", is(50)))
		.andExpect(jsonPath("$.content[2].id", is(3)))
        .andExpect(jsonPath("$.content[2].name", is("itinerary test 3")))
        .andExpect(jsonPath("$.content[2].description", is("lorem ipsum 3")))
        .andExpect(jsonPath("$.content[2].budget", is(100.)))
        .andExpect(jsonPath("$.content[2].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[2].views", is(15)));
	}
	
	@Test
	void testSearchItinerariesByName() throws Exception {
		this.mockMvc.perform(get("/itinerary/searchByName?page={page}&size={size}&name={name}", TEST_ITINERARY_PAGE, TEST_ITINERARY_SIZE, TEST_ITINERARY_NAME))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("itinerary test 1")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.content[0].budget", is(10.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[0].views", is(0)));
		
	}
	
	@Test
	void testListItinerariesByUserId() throws Exception {
		this.mockMvc.perform(get("/itinerary/searchByUserId?page={page}&size={size}&userId={userId}", TEST_ITINERARY_PAGE, TEST_ITINERARY_SIZE, TEST_ITINERARY_USER_ID))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("itinerary test 1")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.content[0].budget", is(10.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[0].views", is(0)))
        .andExpect(jsonPath("$.content[1].id", is(3)))
        .andExpect(jsonPath("$.content[1].name", is("itinerary test 3")))
        .andExpect(jsonPath("$.content[1].description", is("lorem ipsum 3")))
        .andExpect(jsonPath("$.content[1].budget", is(100.)))
        .andExpect(jsonPath("$.content[1].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[1].views", is(15)));
		
	}
	
	@Test
	void testListItinerariesByUsername() throws Exception {
		this.mockMvc.perform(get("/itinerary/user/{username}?page={page}&size={size}", TEST_ITINERARY_USERNAME, TEST_ITINERARY_PAGE, TEST_ITINERARY_SIZE))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("itinerary test 1")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.content[0].budget", is(10.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[0].views", is(0)))
        .andExpect(jsonPath("$.content[1].id", is(3)))
        .andExpect(jsonPath("$.content[1].name", is("itinerary test 3")))
        .andExpect(jsonPath("$.content[1].description", is("lorem ipsum 3")))
        .andExpect(jsonPath("$.content[1].budget", is(100.)))
        .andExpect(jsonPath("$.content[1].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[1].views", is(15)));
		
	}
	
	@Test
	void testListItinerariesByUsernameDifferentUser() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user2");
		this.mockMvc.perform(get("/itinerary/user/{username}?page={page}&size={size}", TEST_ITINERARY_USERNAME, TEST_ITINERARY_PAGE, TEST_ITINERARY_SIZE))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
		.andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("itinerary test 1")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.content[0].budget", is(10.)))
        .andExpect(jsonPath("$.content[0].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[0].views", is(0)))
        .andExpect(jsonPath("$.content[1].id", is(3)))
        .andExpect(jsonPath("$.content[1].name", is("itinerary test 3")))
        .andExpect(jsonPath("$.content[1].description", is("lorem ipsum 3")))
        .andExpect(jsonPath("$.content[1].budget", is(100.)))
        .andExpect(jsonPath("$.content[1].estimatedDays").doesNotExist())
        .andExpect(jsonPath("$.content[1].views", is(15)));
		
	}

	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testCreateItinerary() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("budget", 0);
		activityJSON.put("description", "lorem ipsum 1");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		
		this.mockMvc.perform(post("/itinerary/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isOk())
        

		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(TEST_ITINERARY_ID_1)))
        .andExpect(jsonPath("$.description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$.name", is("itinerary test 1")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testCreateItineraryHasErrors() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("This is an error", "This is an error");
		activityJSON.put("description", "lorem ipsum 1");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		
		this.mockMvc.perform(post("/itinerary/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isBadRequest())
        
		// Validate the returned fields
        .andExpect(jsonPath("$.budget[0]", is("El campo presupuesto es obligatorio")));
	}
	
	@Test
	void testCreateItineraryAnonymousUser() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("anonymousUser");
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("budget", 0);
		activityJSON.put("description", "lorem ipsum 1");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		
		this.mockMvc.perform(post("/itinerary/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isForbidden())
        
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("El usuario no tiene permiso de creación sin registrarse.")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testCreateItineraryImageNotFound() throws Exception {
		Optional<Image> defaultImage = Optional.empty();
		given(this.imageService.findById(78)).willReturn(defaultImage);
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("budget", 0);
		activityJSON.put("description", "lorem ipsum 1");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		
		this.mockMvc.perform(post("/itinerary/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isNotFound())
        
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("La imagen seleccionada no ha sido encontrada.")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testCreateItineraryErrorWhenCreatingItinerary() throws Exception {
		doReturn(null).when(this.itineraryService).save(any());
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("budget", 0);
		activityJSON.put("description", "lorem ipsum 1");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		
		this.mockMvc.perform(post("/itinerary/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isNotModified())
        
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("Ha ocurrido un error a la hora de actualizar este itinerario.")));
	}
	
	@Test
	void testCreateItineraryNotRegistered() throws Exception {
		
		given(this.userService.getCurrentUsername()).willReturn("Unregistered");
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("budget", 0);
		activityJSON.put("description", "lorem ipsum 1");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		
		this.mockMvc.perform(post("/itinerary/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())

        .andExpect(jsonPath("$.text", is("El usuario debe estar registrado para publicar un itinerario.")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testUpdateItinerary() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("budget", 0);
		activityJSON.put("description", "lorem ipsum 1 actualizado");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1 actualizado");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		activityJSON.put("id", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(put("/itinerary/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isOk())
        

		// Validate the returned fields
		.andExpect(jsonPath("$.id", is(TEST_ITINERARY_ID_1)))
        .andExpect(jsonPath("$.description", is("lorem ipsum 1 actualizado")))
        .andExpect(jsonPath("$.name", is("itinerary test 1 actualizado")));
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	void testAdminUpdateItinerary() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("budget", 0);
		activityJSON.put("description", "lorem ipsum 1 actualizado");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1 actualizado");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		activityJSON.put("id", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(put("/itinerary/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isOk())
        

		// Validate the returned fields
		.andExpect(jsonPath("$.id", is(TEST_ITINERARY_ID_1)))
        .andExpect(jsonPath("$.description", is("lorem ipsum 1 actualizado")))
        .andExpect(jsonPath("$.name", is("itinerary test 1 actualizado")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testUpdateItineraryHasErrors() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("This is an error", "This is an error");
		activityJSON.put("description", "lorem ipsum 1 actualizado");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1 actualizado");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		activityJSON.put("id", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(put("/itinerary/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isBadRequest())
        
		// Validate the returned fields
		.andExpect(jsonPath("$.budget[0]", is("El campo presupuesto es obligatorio")));
	}
	
	@Test
	void testUpdateItineraryAnonymousUser() throws Exception {
		
		given(this.userService.getCurrentUsername()).willReturn("anonymousUser");
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("budget", "0");
		activityJSON.put("description", "lorem ipsum 1 actualizado");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1 actualizado");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		activityJSON.put("id", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(put("/itinerary/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isForbidden())
        
		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El usuario no tiene permiso de modificación sin registrarse.")));
	}
	
	@Test
	void testUpdateItineraryErrorWhenUpdatingItinerary() throws Exception {
		
		doReturn(null).when(this.itineraryService).save(any());
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("budget", "0");
		activityJSON.put("description", "lorem ipsum 1 actualizado");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1 actualizado");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		activityJSON.put("id", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(put("/itinerary/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isNotModified())
        
		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("Ha ocurrido un error a la hora de actualizar este itinerario.")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testUpdateItineraryNotFound() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("budget", 0);
		activityJSON.put("description", "lorem ipsum 1 actualizado");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1 actualizado");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		activityJSON.put("id", TEST_ITINERARY_ID_NOT_FOUND);
		
		this.mockMvc.perform(put("/itinerary/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
        
//		// Validate the returned fields
		.andExpect(jsonPath("$.text", is(ITINERARY_NOT_FOUND)));
	}
	
	@Test
	void testUpdateItineraryNotRegistered() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user99");
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("budget", 0);
		activityJSON.put("description", "lorem ipsum 1 actualizado");
		activityJSON.put("estimatedDays", 1);
		activityJSON.put("name", "itinerary test 1 actualizado");
		activityJSON.put("recomendedSeason", "WINTER");
		activityJSON.put("status", "DRAFT");
		activityJSON.put("id", TEST_ITINERARY_ID_1);
		
		this.mockMvc.perform(put("/itinerary/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
        
//		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El usuario no tiene permiso de modificación de este itinerario, que no es suyo.")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testDeleteItinerary() throws Exception {
		
		this.mockMvc.perform(delete("/itinerary/delete/{id}", TEST_ITINERARY_ID_1))
		
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

//		// Validate the returned fields
		.andExpect(jsonPath("$.text", is(ITINERARY_DELETED)));
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	void testAdminDeleteItinerary() throws Exception {
		
		this.mockMvc.perform(delete("/itinerary/delete/{id}", TEST_ITINERARY_ID_1))
		
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		// Validate the returned fields
		.andExpect(jsonPath("$.text", is(ITINERARY_DELETED)));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testDeleteItineraryNotFound() throws Exception {
		
		this.mockMvc.perform(delete("/itinerary/delete/{id}", TEST_ITINERARY_ID_NOT_FOUND))
		
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

//		// Validate the returned fields
		.andExpect(jsonPath("$.text", is(ITINERARY_NOT_FOUND)));
	}
	
	@Test
	void testDeleteItineraryNotRegistered() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user99");
		
		this.mockMvc.perform(delete("/itinerary/delete/{id}", TEST_ITINERARY_ID_1))
		
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

//		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("No puede borrar un itinerario que no es suyo")));
	}
	
}