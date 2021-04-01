package com.yourney.controller;

import static org.mockito.BDDMockito.given;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.test.web.servlet.ResultMatcher;

import com.yourney.config.SecurityConfig;
import com.yourney.model.Activity;
import com.yourney.model.Image;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
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
	
	@MockBean
	protected LandmarkService landmarkService;
	
	@MockBean
	protected ImageService imageService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
//		
//		//Image
//	    Image i1 = new Image();
//		
//	    i1.setId(1);
//	    i1.setName("Imagen por defecto");
//	    i1.setImageUrl("https://www.sinrumbofijo.com/wp-content/uploads/2016/05/default-placeholder.png");
//	    i1.setCloudinaryId(null);
//	    
//	    //Landmark
//	    Landmark l1 = new Landmark();
//	    l1.setId((long)1);
//	    l1.setName("Giralda");
//	    l1.setDescription("lorem ipsum");
//	    l1.setPrice(0.);
//	    l1.setCountry("España");
//	    l1.setCity("Sevilla");
//	    l1.setLatitude(37.38618100597202);
//	    l1.setLongitude(-5.992615925346369);
//	    l1.setPromoted(true);
//	    l1.setEmail("giralda@email.com");
//	    l1.setInstagram(null);
//	    l1.setPhone("123456789");
//	    l1.setTwitter(null);
//	    l1.setWebsite(null);
//	    l1.setCategory("Monumento histórico");
//	    l1.setViews((long)0);
//	    l1.setImage(i1);
		
	    //Itinerary
//	    (name, description, status, recommended_season, budget, estimated_days, create_date, views, author_id, image_id) VALUES
//	    ('itinerary test 0', 'lorem ipsum', 'PUBLISHED', 'WINTER', 10., 2, '2021-01-20 12:25:01', 4, 3, 2),       -- 1
	    Itinerary it1 = new Itinerary();
	    
	    it1.setId((long)1);
	    it1.setName("itinerary test 0");
	    it1.setDescription("lorem ipsum");
	    it1.setStatus(StatusType.PUBLISHED);
	    it1.setBudget(10.);
	    it1.setEstimatedDays(2);
	    it1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it1.setViews(0);
	    
	    
	    given(this.itineraryService.findById((long)ActivityControllerTests.TEST_ITINERARY_ID)).willReturn(Optional.of(it1));
	    
		//Activity
		Activity a1 = new Activity(); 

		a1.setId(1);
		a1.setTitle("comienza el test: Giralda");
		a1.setDescription("lorem ipsum 0");
		a1.setDay(1);
		a1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		a1.setItinerary(it1);
//		a1.setLandmark(l1);
		given(this.activityService.findById((long)ActivityControllerTests.TEST_ACTIVITY_ID)).willReturn(Optional.of(a1));
		Collection<Activity> activities = new ArrayList<>();
		activities.add(a1);
		
		Activity a2 = new Activity(); 

		a2.setId(2);
		a2.setTitle("termina el test: Giralda");
		a2.setDescription("lorem ipsum 1");
		a2.setDay(2);
		a2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		a2.setItinerary(it1);
//		a1.setLandmark(l1);
		given(this.activityService.findById((long)ActivityControllerTests.TEST_ACTIVITY_ID_2)).willReturn(Optional.of(a2));
		activities.add(a2);
		
		it1.setActivities(activities);


	}
	
	@Test
	void testShowActivityList() throws Exception {
		this.mockMvc.perform(get("/activity/list")).andExpect(status().isOk()).andExpect(model().attributeExists("activity"));
	}
	
	@Test
	void testShowActivityListByItinerary() throws Exception {
		this.mockMvc.perform(get("/activity/listByItinerary?itineraryId={itineraryId}", TEST_ITINERARY_ID))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate headers
		//.andExpect(header().string(HttpHeaders.LOCATION, "/rest/widgets"))

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

}
