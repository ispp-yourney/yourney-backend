package com.yourney.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourney.config.SecurityConfig;
import com.yourney.model.Activity;
import com.yourney.model.Image;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
import com.yourney.security.model.User;
import com.yourney.service.ActivityService;
import com.yourney.service.ImageService;
import com.yourney.service.ItineraryService;
import com.yourney.service.LandmarkService;


@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerTests {

	private static final int TEST_ACTIVITY_ID = 1;
	private static final int TEST_ACTIVITY_ID_2 = 2;
	private static final int TEST_ITINERARY_ID = 1;
	
	@Autowired
	protected ActivityController activityController;

	@MockBean
	private ActivityService activityService;
	
	@MockBean
	protected ItineraryService itineraryService;
	
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		
		//User
		User auth1 = new User();
		
		auth1.setId((long)1);
		auth1.setEmail("test222@ewfwef.com");
		auth1.setFirstName("Name 1");
		auth1.setLastName("Surname 1");
		auth1.setPassword("user1");
		auth1.setUsername("user1");
		auth1.setPlan(0);
		
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
		Activity a1 = new Activity(); 

		a1.setId(1);
		a1.setTitle("comienza el test: Giralda");
		a1.setDescription("lorem ipsum 0");
		a1.setDay(1);
		a1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		a1.setItinerary(it1);
		given(this.activityService.findById((long)ActivityControllerTests.TEST_ACTIVITY_ID)).willReturn(Optional.of(a1));
		Collection<Activity> activities = new ArrayList<>();
		activities.add(a1);
		
		doReturn(a1).when(this.activityService).save(a1);
		
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
	@WithMockUser(username = "user1", password = "user1")
	void testCreateActivity() throws Exception {
		Activity activityToPost = new Activity();
		
		activityToPost.setId(1);
		activityToPost.setTitle("comienza el test: Giralda");
		activityToPost.setDescription("lorem ipsum 0");
		activityToPost.setDay(1);
		
		this.mockMvc.perform(post("/activity/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content("{\n" + 
				"  \"day\": 1,\n" + 
				"  \"description\": \"string\",\n" + 
				"  \"id\": 1,\n" + 
				"  \"itinerary\": 1,\n" + 
				"  \"landmark\": null,\n" + 
				"  \"title\": \"string\"\n" + 
				"}"))

		// Validate the response code and content type
		.andExpect(status().isOk());
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON))	

//		// Validate the returned fields
//        .andExpect(jsonPath("$.id", is(1)))
//        .andExpect(jsonPath("$.title", is("comienza el test: Giralda")))
//        .andExpect(jsonPath("$.description", is("lorem ipsum 0")))
//        .andExpect(jsonPath("$.day", is(1)));
	}
}
