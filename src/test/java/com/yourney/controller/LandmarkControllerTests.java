package com.yourney.controller;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
import org.springframework.test.web.servlet.MockMvc;
import com.yourney.model.Landmark;
import com.yourney.model.projection.LandmarkProjection;
import com.yourney.service.ActivityService;
import com.yourney.service.ImageService;
import com.yourney.service.ItineraryService;
import com.yourney.service.LandmarkService;


@SpringBootTest
@AutoConfigureMockMvc
class LandmarkControllerTests {

	private static final int TEST_LANDMARK_ID = 1;
	private static final String TEST_LANDMARK_COUNTRY = "España";
	private static final String TEST_LANDMARK_CITY = "Sevilla";
	private static final String TEST_LANDMARK_NAME = "Giralda";
	private static final int TEST_LANDMARK_PAGE = 1;
	private static final int TEST_LANDMARK_SIZE = 10;
	
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

		//Landmark
	    Landmark l1 = new Landmark();
	    l1.setId((long)1);
	    l1.setName("Giralda");
	    l1.setDescription("lorem ipsum");
	    l1.setPrice(0.);
	    l1.setCountry("España");
	    l1.setCity("Sevilla");
	    l1.setLatitude(37.38618100597202);
	    l1.setLongitude(-5.992615925346369);
	    l1.setEndPromotionDate(LocalDateTime.of(2025, 12, 10, 8, 6, 4));
	    l1.setEmail("giralda@email.com");
	    l1.setInstagram(null);
	    l1.setPhone("123456789");
	    l1.setTwitter(null);
	    l1.setWebsite(null);
	    l1.setCategory("Monumento histórico");
	    l1.setViews((long)0);
	    l1.setImage(null);
	    
	    Landmark l2 = new Landmark();
	    l2.setId((long)2);
	    l2.setName("Coliseo romano");
	    l2.setDescription("lorem ipsum");
	    l2.setPrice(0.);
	    l2.setCountry("Italia");
	    l2.setCity("Roma");
	    l2.setLatitude(37.38618100597202);
	    l2.setLongitude(-5.992615925346369);
	    l2.setEndPromotionDate(LocalDateTime.of(2025, 12, 10, 8, 6, 4));
	    l2.setEmail("coliseo@email.com");
	    l2.setInstagram(null);
	    l2.setPhone("123456789");
	    l2.setTwitter(null);
	    l2.setWebsite(null);
	    l2.setCategory("Monumento histórico");
	    l2.setViews((long)0);
	    l2.setImage(null);
	    
	    Collection<String> countryList = new ArrayList<>();
	    countryList.add(l1.getCountry());
	    countryList.add(l2.getCountry());
	    
	    Collection<String> cityByCountryList = new ArrayList<>();
	    cityByCountryList.add(l1.getCity());
	    
	    Collection<String> cityList = new ArrayList<>();
	    cityList.add(l1.getCity());
	    cityList.add(l2.getCity());
	    
	    Pageable pageable = PageRequest.of(TEST_LANDMARK_PAGE, TEST_LANDMARK_SIZE);
		ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
		
		List<LandmarkProjection> landmarks1 = new ArrayList<>();
		LandmarkProjection lap1 = factory.createProjection(LandmarkProjection.class, l1);
		landmarks1.add(lap1);
		Page<LandmarkProjection> landmarksPage1 = new PageImpl<>(landmarks1);
	    
	    given(this.landmarkService.findById((long)LandmarkControllerTests.TEST_LANDMARK_ID)).willReturn(Optional.of(l1));
	    given(this.landmarkService.findAllCountries()).willReturn(countryList);
	    given(this.landmarkService.findCitiesByCountry(TEST_LANDMARK_COUNTRY)).willReturn(cityByCountryList);
	    given(this.landmarkService.findAllCities()).willReturn(cityList);
	    given(this.landmarkService.searchByProperties("%" + TEST_LANDMARK_COUNTRY + "%", "%" + TEST_LANDMARK_CITY + "%","%" + TEST_LANDMARK_NAME + "%", TEST_LANDMARK_SIZE, pageable)).willReturn(landmarksPage1);
		
	}
	
	@Test
	void testListCountries() throws Exception {
		this.mockMvc.perform(get("/landmark/country/list"))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))

		// Validate the returned fields
        .andExpect(jsonPath("$", isA(ArrayList.class)))
        .andExpect(jsonPath("$[0]", is("España")))
        .andExpect(jsonPath("$[1]", is("Italia")));
	}
	

	@Test
	void testListCitiesByCountry() throws Exception {
		this.mockMvc.perform(get("/landmark/country/{name}/city/list", TEST_LANDMARK_COUNTRY))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))

		// Validate the returned fields
        .andExpect(jsonPath("$", isA(ArrayList.class)))
        .andExpect(jsonPath("$[0]", is("Sevilla")));
	}
	
	@Test
	void testListCities() throws Exception {
		this.mockMvc.perform(get("/landmark/city/list"))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))

		// Validate the returned fields
        .andExpect(jsonPath("$", isA(ArrayList.class)))
        .andExpect(jsonPath("$[0]", is("Sevilla")))
        .andExpect(jsonPath("$[1]", is("Roma")));
	}
	
	@Test
	void testSearchByPropertiesLandMark() throws Exception {
		this.mockMvc.perform(get("/landmark/search?page={page}&size={size}&country={country}&city={city}&name={name}", TEST_LANDMARK_PAGE, TEST_LANDMARK_SIZE, TEST_LANDMARK_COUNTRY, TEST_LANDMARK_CITY, TEST_LANDMARK_NAME))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		// Validate the returned fields
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("Giralda")))
        .andExpect(jsonPath("$.content[0].description", is("lorem ipsum")))
        .andExpect(jsonPath("$.content[0].price", is(0.0)))
        .andExpect(jsonPath("$.content[0].country", is("España")))
        .andExpect(jsonPath("$.content[0].city", is("Sevilla")));
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