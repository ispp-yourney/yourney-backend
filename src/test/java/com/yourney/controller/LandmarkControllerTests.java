package com.yourney.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
public class LandmarkControllerTests {

	private static final int TEST_ACTIVITY_ID = 1;
	private static final int TEST_ACTIVITY_ID_2 = 2;
	private static final int TEST_ITINERARY_ID = 1;
	private static final int TEST_LANDMARK_ID = 1;
	
	@Autowired
	protected LandmarkController landmarkController;

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
	    Landmark l1 = new Landmark();
	    l1.setId((long)1);
	    l1.setName("Giralda");
	    l1.setDescription("lorem ipsum");
	    l1.setPrice(0.);
	    l1.setCountry("España");
	    l1.setCity("Sevilla");
	    l1.setLatitude(37.38618100597202);
	    l1.setLongitude(-5.992615925346369);
	    l1.setPromoted(true);
	    l1.setEmail("giralda@email.com");
	    l1.setInstagram(null);
	    l1.setPhone("123456789");
	    l1.setTwitter(null);
	    l1.setWebsite(null);
	    l1.setCategory("Monumento histórico");
	    l1.setViews((long)0);
	    l1.setImage(null);
	    
	    Collection<String> countryList = new ArrayList<>();
	    countryList.add(l1.getCountry());
	    
	    given(this.landmarkService.findById((long)LandmarkControllerTests.TEST_LANDMARK_ID)).willReturn(Optional.of(l1));
	    
	    given(this.landmarkService.findAllCountries()).willReturn(countryList);
		
	}
	
	@Test
	void testListCountries() throws Exception {
		this.mockMvc.perform(get("/landmark/country/list"))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))

		// Validate the returned fields
        .andExpect(jsonPath("$", isA(ArrayList.class)));
	}
	
	@Test
	void testShowLandMark() throws Exception {
		this.mockMvc.perform(get("/landmark/show/{id}", TEST_LANDMARK_ID))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("Giralda")))
        .andExpect(jsonPath("$.description", is("lorem ipsum")))
        .andExpect(jsonPath("$.price", is(0.0)))
        .andExpect(jsonPath("$.country", is("España")))
        .andExpect(jsonPath("$.city", is("Sevilla")));
	}

	
}