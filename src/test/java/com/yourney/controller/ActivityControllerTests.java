
package com.yourney.controller;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.yourney.model.Activity;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
import com.yourney.security.model.Role;
import com.yourney.security.model.RoleType;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;
import com.yourney.service.ActivityService;
import com.yourney.service.ItineraryService;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@AutoConfigureMockMvc
class ActivityControllerTests {

	private static final int TEST_ACTIVITY_ID = 1;
	private static final int TEST_ACTIVITY_ID_NOT_FOUND = 10;
	private static final int TEST_ACTIVITY_ID_2 = 2;
	private static final int TEST_ITINERARY_ID = 1;
	
	@Autowired
	protected ActivityController activityController;

	@MockBean
	private ActivityService activityService;
	
	@MockBean
	private UserService userService;

	@MockBean
	protected ItineraryService itineraryService;
	
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
		
		//User
		User auth1 = new User();
		
		auth1.setId((long)1);
		auth1.setEmail("testuser@email.com");
		auth1.setFirstName("Name 1");
		auth1.setLastName("Surname 1");
		auth1.setPassword("user1");
		auth1.setUsername("user1");
		auth1.setPlan(0);
		
		User admin = new User();
		admin.setId((long)2);
		admin.setId((long)2);
		admin.setEmail("admin@email.com");
		admin.setFirstName("Name 3");
		admin.setLastName("Surname 3");
		admin.setPassword("admin");
		admin.setUsername("admin");
		admin.setPlan(0);
		admin.setRoles(rolesAdmin);

	    //Itinerary
	    Itinerary it1 = new Itinerary();
	    
	    it1.setId((long)1);
	    it1.setName("itinerary test 0");
	    it1.setDescription("lorem ipsum");
	    it1.setStatus(StatusType.PUBLISHED);
	    it1.setBudget(10.);
	    it1.setEstimatedDays(2);
	    it1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it1.setViews(0);
	    it1.setAuthor(auth1);
	    
	    given(this.itineraryService.findById((long)ActivityControllerTests.TEST_ITINERARY_ID)).willReturn(Optional.of(it1));
	    
		//Activity
	    given(this.activityService.findById((long)ActivityControllerTests.TEST_ACTIVITY_ID_NOT_FOUND)).willReturn(Optional.empty());
	    
		Activity a1 = new Activity(); 

		a1.setId((long) 1);
		a1.setTitle("comienza el test: Giralda");
		a1.setDescription("lorem ipsum 0");
		a1.setDay(1);
		a1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		a1.setItinerary(it1);
		given(this.activityService.findById((long) TEST_ACTIVITY_ID)).willReturn(Optional.of(a1));
		given(this.activityService.existsById((long)ActivityControllerTests.TEST_ACTIVITY_ID)).willReturn(true);
		Collection<Activity> activities = new ArrayList<>();
		activities.add(a1);
				
		doReturn(a1).when(this.activityService).save(any());
		
		Activity a2 = new Activity(); 

		a2.setId(2);
		a2.setTitle("termina el test: Giralda");
		a2.setDescription("lorem ipsum 1");
		a2.setDay(2);
		a2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		a2.setItinerary(it1);
		given(this.activityService.findById((long)ActivityControllerTests.TEST_ACTIVITY_ID_2)).willReturn(Optional.of(a2));
		activities.add(a2);
		
		it1.setActivities(activities);
		
		given(this.activityService.findActivityByItinerary((long)ActivityControllerTests.TEST_ITINERARY_ID)).willReturn(activities);
		
		Iterable<Activity> activitiesList = new ArrayList<Activity>(activities);
	
		given(this.activityService.findAllActivityProjectionsByDayAndItinerary((long)ActivityControllerTests.TEST_ITINERARY_ID, 1)).willReturn(activitiesList);
		
		given(this.userService.getCurrentUsername()).willReturn("anonymousUser");
		given(this.userService.getByUsername("user1")).willReturn(Optional.of(auth1));
		given(this.userService.getByUsername("admin")).willReturn(Optional.of(admin));
		// LADNMARKS
	    
	    Landmark la1 = new Landmark();
	    
	    la1.setCategory("category 1");
	    la1.setCity("Sevilla");
	    la1.setCountry("España");
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

	}
	
	@Test
	void testListActivitiesByItineraryAndDay() throws Exception {
		this.mockMvc.perform(get("/activity/list?itineraryId={itineraryId}", TEST_ITINERARY_ID))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].title", is("comienza el test: Giralda")))
        .andExpect(jsonPath("$[0].description", is("lorem ipsum 0")))
        .andExpect(jsonPath("$[0].day", is(1)));
	}

	@Test
	void testListActivityListByItinerary() throws Exception {
		this.mockMvc.perform(get("/activity/listByItinerary?itineraryId={itineraryId}", TEST_ITINERARY_ID))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].title", is("comienza el test: Giralda")))
        .andExpect(jsonPath("$[0].description", is("lorem ipsum 0")))
        .andExpect(jsonPath("$[0].day", is(1)))
        .andExpect(jsonPath("$[1].id", is(2)))
        .andExpect(jsonPath("$[1].title", is("termina el test: Giralda")))
        .andExpect(jsonPath("$[1].description", is("lorem ipsum 1")))
        .andExpect(jsonPath("$[1].day", is(2)));
	}
	
	@Test
	void testShowActivity() throws Exception {
		this.mockMvc.perform(get("/activity/show/{id}", TEST_ACTIVITY_ID))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.title", is("comienza el test: Giralda")))
        .andExpect(jsonPath("$.description", is("lorem ipsum 0")))
        .andExpect(jsonPath("$.day", is(1)));
	}
	
	@Test
	void testShowActivityIdNotFound() throws Exception {
		this.mockMvc.perform(get("/activity/show/{id}", TEST_ACTIVITY_ID_NOT_FOUND))
		// Validate the response code and content type
		.andExpect(status().isNotFound());
	
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testCreateActivity() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user1");
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("day", 1);
		activityJSON.put("description", "lorem ipsum 0");
		activityJSON.put("itinerary", 1);
		activityJSON.put("title", "comienza el test: Giralda");
		activityJSON.put("landmark", 0);
		
		this.mockMvc.perform(post("/activity/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))


		// Validate the response code and content type
		.andExpect(status().isOk())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.title", is("comienza el test: Giralda")))
        .andExpect(jsonPath("$.description", is("lorem ipsum 0")))
        .andExpect(jsonPath("$.day", is(1)));
	}
	
	@Test
	void testCreateActivityNotRegistered() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("day", 1);
		activityJSON.put("description", "lorem ipsum 0");
		activityJSON.put("itinerary", 1);
		activityJSON.put("title", "comienza el test: Giralda");
		activityJSON.put("landmark", 0);
		
		this.mockMvc.perform(post("/activity/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.text", is("El usuario no tiene permiso de creación sin registrarse.")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testUpdateActivity() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user1");
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("day", 1);
		activityJSON.put("description", "Descripcion de prueba");
		activityJSON.put("title", "Titulo de prueba");
		activityJSON.put("id", TEST_ACTIVITY_ID);
		
		this.mockMvc.perform(put("/activity/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("La actividad ha sido actualizada con éxito")));
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	void testAdminUpdateActivity() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("admin");
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("day", 1);
		activityJSON.put("description", "Descripcion de prueba");
		activityJSON.put("title", "Titulo de prueba");
		activityJSON.put("id", TEST_ACTIVITY_ID);
		
		this.mockMvc.perform(put("/activity/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("La actividad ha sido actualizada con éxito")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testUpdateActivityNotFound() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user1");
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("day", 1);
		activityJSON.put("description", "Descripcion de prueba");
		activityJSON.put("title", "Titulo de prueba");
		activityJSON.put("id", TEST_ACTIVITY_ID_NOT_FOUND);
		
		this.mockMvc.perform(put("/activity/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("La actividad indicada no existe")));
	}
	
	@Test
	void testUpdateActivityNotRegistered() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("day", 1);
		activityJSON.put("description", "Descripcion de prueba");
		activityJSON.put("title", "Titulo de prueba");
		activityJSON.put("id", TEST_ACTIVITY_ID);
		
		this.mockMvc.perform(put("/activity/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("No puede añadir una actividad a un itinerario del que no es dueño.")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testDeleteActivity() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user1");
		this.mockMvc.perform(delete("/activity/delete/{id}", TEST_ACTIVITY_ID))

		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("Actividad eliminada correctamente")));
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	void testAdminDeleteActivity() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("admin");
		this.mockMvc.perform(delete("/activity/delete/{id}", TEST_ACTIVITY_ID))

		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("Actividad eliminada correctamente")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testDeleteActivityNotFound() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user1");
		this.mockMvc.perform(delete("/activity/delete/{id}", TEST_ACTIVITY_ID_NOT_FOUND))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("No existe la actividad indicada")));
	}
	
	@Test
	void testDeleteActivityNotRegistered() throws Exception {
		
		this.mockMvc.perform(delete("/activity/delete/{id}", TEST_ACTIVITY_ID))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("No puede eliminar una actividad de un itinerario del que no es creador.")));
	}
}

