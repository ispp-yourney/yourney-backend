package com.yourney.controller;

import static org.mockito.BDDMockito.given;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.yourney.config.SecurityConfig;
import com.yourney.service.ActivityService;

@WebMvcTest(value = ActivityController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfig.class)
public class AirportControllerTests {

	private static final int TEST_ACTIVITY_ID = 1;

	@Autowired
	protected ActivityController activityController;

	@MockBean
	private ActivityService activityService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
//		Activity a1 = new Activity(); //'Sevilla Airport', 50, 600, 37.4180000, -5.8931100, 'SVQ', 'Sevilla'
//
//		a1.setId(1);
//		a1.setName("Sevilla Airport");
//		a1.setMaxNumberOfPlanes(50);
//		a1.setMaxNumberOfClients(600);
//		a1.setLatitude(37.4180000);
//		a1.setLongitude(-5.8931100);
//		a1.setCode("SVQ");
//		a1.setCity("Sevilla");
//
//		given(this.airportService.findAirportById(AirportControllerTests.TEST_ACTIVITY_ID)).willReturn(a1);
//		
//		
//		Forecast f1 = new Forecast();
//		f1.setBase("base");
//		
//		Clouds c = new Clouds();
//		c.setAll(0);
//		Coord co = new Coord();
//		co.setLat(0);
//		co.setLon(0);
//		Main m = new Main();
//		m.setFeels_like(0);
//		m.setHumidity(0);
//		m.setPressure(0);
//		m.setTemp(0);
//		m.setTemp_max(0);
//		m.setTemp_min(0);
//		Sys s = new Sys();
//		s.setCountry("country");
//		s.setSunrise(0);
//		s.setSunset(0);
//		s.setType(0);
//		Wind w = new Wind();
//		w.setDeg(0);
//		w.setSpeed(0);
//	
//		f1.setClouds(c);
//		f1.setCod(0);
//		f1.setCoord(co);
//		f1.setDt(0);
//		f1.setMain(m);
//		f1.setName("name");
//		f1.setSys(s);
//		f1.setTimezone(0);
//		f1.setVisibility(0);
//		f1.setWind(w);
//		
//		given(this.forecastService.searchForecastByCity(a1.getCity())).willReturn(mono);
//		
//		given(this.forecastService.searchForecastByCity(a1.getCity()).block()).willReturn(f1);
//		
//
//	}
//
//	@WithMockUser(username = "client1", authorities = {
//			"airline"
//		})
//	@Test
//	void testShowAirportList() throws Exception {
//		this.mockMvc.perform(get("/airports")).andExpect(status().isOk()).andExpect(model().attributeExists("airports"))
//				.andExpect(view().name("airports/airportList"));
//	}
//
//	@WithMockUser(username = "client1", authorities = {
//			"airline"
//		})
//	@Test
//	void testShowAirport() throws Exception {
//		this.mockMvc.perform(get("/airports/{airportId}", AirportControllerTests.TEST_AIRPORT_ID))
//		.andExpect(status().isOk()).andExpect(model().attributeExists("airport")).andExpect(view().name("airports/airportDetails"));
//	}
//
//	@WithMockUser(username = "airline1", authorities = {
//			"airline"
//		})
//	@Test
//	void testInitCreationForm() throws Exception {
//		this.mockMvc.perform(get("/airports/new")).andExpect(status().isOk())
//				.andExpect(view().name("airports/createAirportForm"));
//	}
//	
//	@WithMockUser(username = "airline1", authorities = {
//			"airline"
//		})
//	@Test
//	void testProcessCreationFormSuccess() throws Exception {
//		this.mockMvc.perform(post("/airports/new").with(csrf())
//
//				.param("name", "Sevilla Airport").param("maxNumberOfPlanes", "50")
//				.param("maxNumberOfClients", "600").param("latitude", "37.4180000").param("longitude", "-5.8931100")
//				.param("code", "SVQ").param("city", "Sevilla")).andExpect(status().is3xxRedirection());;
//	}
//	
//	@WithMockUser(username = "airline1", authorities = {
//			"airline"
//		})
//	@ParameterizedTest 
//	@CsvSource({
//	    "Madrid Airport, 35, 500, 55.55, 49.112, MAC, Madrid",
//	    "Tongoliki Airport, 25, 350, 72.10, 87.123, ATC, Togoliki Menor",
//	    "Chicago Airport, 40, 650, 82.92, -73.9012, CKP, Chicago",
//	    "Arellano Airport, 1, 1, -1.1111111, 1.1111111, EGD, La casa de Dani",
//	}) 
//	void testProcessCreationFormSuccess(String name, String maxNumberOfPlanes, String maxNumberOfClients, String latitude, 
//			String longitude, String code, String city) throws Exception {    
//		this.mockMvc.perform(post("/airports/new").with(csrf())
//				.param("name", name)
//				.param("maxNumberOfPlanes", maxNumberOfPlanes)
//				.param("maxNumberOfClients", maxNumberOfClients)
//				.param("latitude", latitude)
//				.param("longitude", longitude)
//				.param("code", code)
//				.param("city", city))
//
//				.andExpect(status().is3xxRedirection());
//	} 
//
//	@WithMockUser(username = "airline1", authorities = {
//			"airline"
//		})
//	@Test
//	void testProcessCreationFormHasErrors() throws Exception {
//		this.mockMvc.perform(post("/airports/new").with(csrf())
//
//				.param("name", "Sevilla Airport").param("maxNumberOfPlanes", "200")
//				.param("maxNumberOfClients", "description").param("latitude", "190.123").param("longitude", "78.987")
//				.param("code", "VGA").param("city", "Sevilla")).andExpect(model().attributeHasErrors("airport"))
//				.andExpect(view().name("airports/createAirportForm"));
//	}
//	
//	@WithMockUser(username = "airline1", authorities = {
//			"airline"
//		})
//	@ParameterizedTest 
//	@CsvSource({
//	    "Madrid Airport, 35, 500, -100.89, 49.112, MAC, Madrid",
//	    "Tongoliki Airport, 25, 350, 72.10, 200.9172, ATC, Togoliki Menor",
//	    "Chicago Airport, 40, -12, 82.92, -73.9012, CKP, Chicago",
//	    "Arellano Airport, -1000, 1, -1.1111111, 1.1111111, EGD, La casa de Dani",
//	}) 
//	void testProcessCreationFormHasErrors(String name, String maxNumberOfPlanes, String maxNumberOfClients, String latitude, 
//			String longitude, String code, String city) throws Exception {    
//		this.mockMvc.perform(post("/airports/new").with(csrf())
//				.param("name", name)
//				.param("maxNumberOfPlanes", maxNumberOfPlanes)
//				.param("maxNumberOfClients", maxNumberOfClients)
//				.param("latitude", latitude)
//				.param("longitude", longitude)
//				.param("code", code)
//				.param("city", city))
//		
//				.andExpect(model().attributeHasErrors("airport"))
//				.andExpect(view().name("airports/createAirportForm"));
//	} 
//
//	@WithMockUser(username = "airline1", authorities = {
//			"airline"
//		})
//	@Test
//	void testInitUpdateForm() throws Exception {
//		mockMvc.perform(get("/airports/{airportId}/edit", TEST_AIRPORT_ID)).andExpect(status().isOk())
//				.andExpect(model().attributeExists("airport")).andExpect(view().name("airports/createAirportForm"));
//	}
//
//	@WithMockUser(username = "airline1", authorities = {
//			"airline"
//		})
//	@Test
//	void testProcessUpdateFormSuccess() throws Exception {
//		mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf())
//				.param("name", "Sevilla Airports").param("maxNumberOfPlanes", "201")
//				.param("maxNumberOfClients", "100").param("latitude", "11.98").param("longitude", "78.987")
//				.param("code", "VBA").param("city", "Madrid"))
//		
//				.andExpect(status().is3xxRedirection())
//				.andExpect(view().name("redirect:/airports/{airportId}"));
//	}
//	
//	@WithMockUser(username = "airline1", authorities = {
//			"airline"
//		})
//	@ParameterizedTest 
//	@CsvSource({
//	    "Madrid Airport, 35, 500, 55.55, 49.112, MAC, Madrid",
//	    "Tongoliki Airport, 25, 350, 72.10, 87.123, ATC, Togoliki Menor",
//	    "Chicago Airport, 40, 650, 82.92, -73.9012, CKP, Chicago",
//	    "Arellano Airport, 1, 1, -1.1111111, 1.1111111, EGD, La casa de Dani",
//	}) 
//	void testProcessUpdateFormSuccess(String name, String maxNumberOfPlanes, String maxNumberOfClients, String latitude, 
//			String longitude, String code, String city) throws Exception {    
//		this.mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf())
//				.param("name", name)
//				.param("maxNumberOfPlanes", maxNumberOfPlanes)
//				.param("maxNumberOfClients", maxNumberOfClients)
//				.param("latitude", latitude)
//				.param("longitude", longitude)
//				.param("code", code)
//				.param("city", city))
//
//				.andExpect(status().is3xxRedirection())
//				.andExpect(view().name("redirect:/airports/{airportId}"));
//	} 
//
//	@WithMockUser(username = "airline1", authorities = {
//			"airline"
//		})
//	@Test
//	void testProcessUpdateFormHasErrors() throws Exception {
//		mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf()).param("name", "Betis Airport")
//				.param("code", "DEP").param("latitude", "190.345")).andExpect(model().attributeHasErrors("airport"))
//				.andExpect(status().isOk()).andExpect(view().name("airports/createAirportForm"));
//	}
//	
//	@WithMockUser(username = "airline1", authorities = {
//			"airline"
//		})
//	@ParameterizedTest 
//	@CsvSource({
//		"123, 35, -2, 10.89, 49.112, MAC, Madrid",
//	    "Tongoliki Airport, 25, 350, 72.10, 200.9172, ATC, Togoliki Menor",
//	    "Chicago Airport, 40, -12, 82.92, -73.9012, CKP, Chicago",
//	    "Arellano Airport, 0, 1, -1.1111111, 1.1111111, 123, La casa de Dani",
//	}) 
//	void testProcessUpdateFormHasErrors(String name, String maxNumberOfPlanes, String maxNumberOfClients, String latitude, 
//			String longitude, String code, String city) throws Exception {    
//		this.mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf())
//				.param("name", name)
//				.param("maxNumberOfPlanes", maxNumberOfPlanes)
//				.param("maxNumberOfClients", maxNumberOfClients)
//				.param("latitude", latitude)
//				.param("longitude", longitude)
//				.param("code", code)
//				.param("city", city))
//				
//				.andExpect(model().attributeHasErrors("airport"))
//				.andExpect(view().name("airports/createAirportForm"));
	} 

}
