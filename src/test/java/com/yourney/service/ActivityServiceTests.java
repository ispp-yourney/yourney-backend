
package com.yourney.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourney.model.Activity;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
import com.yourney.repository.ActivityRepository;
import com.yourney.repository.ItineraryRepository;
import com.yourney.security.model.User;


@SpringBootTest
public class ActivityServiceTests {

	private static final long	TEST_ACTIVITY_ID	= 1;
	private static final long	TEST_ITINERARY_ID	= 1;
	
	@Autowired
	protected ActivityService activityService;
	
	@MockBean
	private ItineraryService itineraryService;

	@MockBean
	private ActivityRepository activityRepository;
	
	@MockBean
	private ItineraryRepository itineraryRepository;


	public Activity activity = new Activity();
	
	@BeforeEach
	void setup() {
		
		//User
		User auth1 = new User();
		
		auth1.setId((long)1);
		auth1.setEmail("testuser@email.com");
		auth1.setFirstName("Name 1");
		auth1.setLastName("Surname 1");
		auth1.setPassword("user1");
		auth1.setUsername("user1");
		auth1.setPlan(0);
		
	    //Itinerary
	    Itinerary it1 = new Itinerary();
	    
	    it1.setId((long)ActivityServiceTests.TEST_ITINERARY_ID);
	    it1.setName("itinerary test 0");
	    it1.setDescription("lorem ipsum");
	    it1.setStatus(StatusType.PUBLISHED);
	    it1.setBudget(10.);
	    it1.setEstimatedDays(2);
	    it1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it1.setViews(0);
	    it1.setAuthor(auth1);
	    
//	    doReturn(Optional.of(it1)).when(this.itineraryRepository).findById(ActivityServiceTests.TEST_ITINERARY_ID);
	    given(this.itineraryRepository.findById(ActivityServiceTests.TEST_ITINERARY_ID)).willReturn(Optional.of(it1));
	    
		//Activity

		this.activity.setId(ActivityServiceTests.TEST_ACTIVITY_ID);
		this.activity.setTitle("comienza el test: Giralda");
		this.activity.setDescription("lorem ipsum 0");
		this.activity.setDay(1);
		this.activity.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		this.activity.setItinerary(it1);
		Collection<Activity> activities = new ArrayList<>();
		activities.add(this.activity);
		
//		doReturn(Optional.of(this.activity)).when(this.activityRepository).findById(ActivityServiceTests.TEST_ACTIVITY_ID);
		given(this.activityRepository.findById(ActivityServiceTests.TEST_ACTIVITY_ID)).willReturn(Optional.of(this.activity));

		
		Activity a2 = new Activity(); 

		a2.setId(2);
		a2.setTitle("termina el test: Giralda");
		a2.setDescription("lorem ipsum 1");
		a2.setDay(2);
		a2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		a2.setItinerary(it1);
		activities.add(a2);
		
		it1.setActivities(activities);
		
		
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
	void testFindById() {

		Optional<Activity> expected = this.activityService.findById(ActivityServiceTests.TEST_ACTIVITY_ID);

		assertTrue(expected.isPresent());
		assertSame(expected.get(), this.activity);
	}

//	@Test
//	@Transactional
//	public void shouldInsertAirport() {
//
//		Airport airport = new Airport();
//		airport.setName("JFK Airport");
//		airport.setMaxNumberOfPlanes(2000);
//		airport.setMaxNumberOfClients(25000);
//		airport.setLatitude(40.642098);
//		airport.setLongitude(-73.789288);
//		airport.setCode("WTF");
//		airport.setCity("New York");
//
//		try {
//			this.airportService.saveAirport(airport);
//		} catch (DataAccessException | IncorrectCartesianCoordinatesException e) {
//			e.printStackTrace();
//		}
//
//		assertThat(airport.getId().longValue()).isNotEqualTo(0);
//
//		Collection<Airport> airports = this.airportService.findAirportsByName("JFK Airport");
//		int found = airports.size();
//
//		assertThat(found).isGreaterThan(0);
//	}
//
//	@Test
//	@Transactional
//	public void shouldThrowInsertExceptionIncorretCardinalCoordinates() {
//
//		Airport airportWithIncorretCardinalCoordinates = new Airport();
//		airportWithIncorretCardinalCoordinates.setName("JFK Airport");
//		airportWithIncorretCardinalCoordinates.setMaxNumberOfPlanes(2000);
//		airportWithIncorretCardinalCoordinates.setMaxNumberOfClients(25000);
//		airportWithIncorretCardinalCoordinates.setLatitude(92.5);
//		airportWithIncorretCardinalCoordinates.setLongitude(-73.789288);
//		airportWithIncorretCardinalCoordinates.setCode("JFK");
//		airportWithIncorretCardinalCoordinates.setCity("New York");
//
//		assertThrows(IncorrectCartesianCoordinatesException.class, () -> {
//			airportService.saveAirport(airportWithIncorretCardinalCoordinates);
//		});
//	}
///*
//	@Test
//	@Transactional
//	public void shouldThrowDuplicatedAirportNameException() {
//		Airport firstAirport = new Airport();
//		firstAirport.setName("JFK Airport");
//		firstAirport.setMaxNumberOfPlanes(2000);
//		firstAirport.setMaxNumberOfClients(25000);
//		firstAirport.setLatitude(17.5);
//		firstAirport.setLongitude(-73.789288);
//		firstAirport.setCode("JFK");
//		firstAirport.setCity("New York");
//
//		try {
//			airportService.saveAirport(firstAirport);
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//		} catch (IncorrectCartesianCoordinatesException e) {
//			e.printStackTrace();
//		} 
//
//		Airport secondAirport = new Airport();
//		secondAirport.setName("JFK Airport");
//		secondAirport.setMaxNumberOfPlanes(100);
//		secondAirport.setMaxNumberOfClients(1000);
//		secondAirport.setLatitude(60.2);
//		secondAirport.setLongitude(-173.789288);
//		secondAirport.setCode("NYC");
//		secondAirport.setCity("New York City");
//
//		assertThrows(DuplicatedAirportNameException.class, () -> {
//			airportService.saveAirport(secondAirport);
//		});
//
//	}
//*/
//	@Test
//	@Transactional
//	public void shouldNotInsertNegativeMaxNumberOfPlanes() {
//		Airport airport = new Airport();
//		airport.setName("JFK Airport");
//		airport.setMaxNumberOfPlanes(-1);
//		airport.setMaxNumberOfClients(25000);
//		airport.setLatitude(17.5);
//		airport.setLongitude(-73.789288);
//		airport.setCode("JFK");
//		airport.setCity("New York");
//
//		assertThrows(ConstraintViolationException.class, () -> {
//			airportService.saveAirport(airport);
//		});
//
//	}
//
//	@Test
//	@Transactional
//	public void shouldNotInsertNegativeMaxNumberOfClients() {
//
//		Airport airport = new Airport();
//		airport.setName("JFK Airport");
//		airport.setMaxNumberOfPlanes(1000);
//		airport.setMaxNumberOfClients(-1);
//		airport.setLatitude(17.5);
//		airport.setLongitude(-73.789288);
//		airport.setCode("JFK");
//		airport.setCity("New York");
//
//		assertThrows(ConstraintViolationException.class, () -> {
//			airportService.saveAirport(airport);
//		});
//
//	}
//
//	@Test
//	@Transactional
//	public void shouldInsert0MaxNumberOfPlanes() {
//		Airport airport = new Airport();
//		airport.setName("JFK Airport");
//		airport.setMaxNumberOfPlanes(0);
//		airport.setMaxNumberOfClients(25000);
//		airport.setLatitude(17.5);
//		airport.setLongitude(-73.789288);
//		airport.setCode("JFK");
//		airport.setCity("New York");
//
//		try {
//			this.airportService.saveAirport(airport);
//		} catch (DataAccessException | IncorrectCartesianCoordinatesException e) {
//			e.printStackTrace();
//		}
//
//		assertThat(airport.getId().longValue()).isNotEqualTo(0);
//
//		Collection<Airport> airports = this.airportService.findAirportsByName("JFK Airport");
//		int found = airports.size();
//
//		assertThat(found).isEqualTo(1);
//
//	}
//
//	@Test
//	@Transactional
//	public void shouldInsert0MaxNumberOfClients() {
//
//		Airport airport = new Airport();
//		airport.setName("JFK Airport");
//		airport.setMaxNumberOfPlanes(1000);
//		airport.setMaxNumberOfClients(0);
//		airport.setLatitude(17.5);
//		airport.setLongitude(-73.789288);
//		airport.setCode("ASB");
//		airport.setCity("New York");
//
//		try {
//			this.airportService.saveAirport(airport);
//		} catch (DataAccessException | IncorrectCartesianCoordinatesException e) {
//			e.printStackTrace();
//		}
//
//		assertThat(airport.getId().longValue()).isNotEqualTo(0);
//
//		Collection<Airport> airports = this.airportService.findAirportsByName("JFK Airport");
//		int found = airports.size();
//
//		assertThat(found).isGreaterThan(0);
//
//	}
//
//	@Test
//	@Transactional
//	public void shouldNotInsertBigMaxNumberOfPlanes() {
//
//		Airport airport = new Airport();
//		airport.setName("JFK Airport");
//		airport.setMaxNumberOfPlanes(30001);
//		airport.setMaxNumberOfClients(12125);
//		airport.setLatitude(17.5);
//		airport.setLongitude(-73.789288);
//		airport.setCode("JFK");
//		airport.setCity("New York");
//
//		assertThrows(ConstraintViolationException.class, () -> {
//			airportService.saveAirport(airport);
//		});
//
//	}
//
//	@Test
//	@Transactional
//	public void shouldNotInsertBigMaxNumberOfClients() {
//		Airport airport = new Airport();
//		airport.setName("JFK Airport");
//		airport.setMaxNumberOfPlanes(12125);
//		airport.setMaxNumberOfClients(30001);
//		airport.setLatitude(17.5);
//		airport.setLongitude(-73.789288);
//		airport.setCode("JFK");
//		airport.setCity("New York");
//
//		assertThrows(ConstraintViolationException.class, () -> {
//			airportService.saveAirport(airport);
//		});
//	}
//
//	@Test
//	@Transactional
//	public void shouldNotInsertCodeWithMoreThan3Letters() {
//		Airport airport = new Airport();
//		airport.setName("JFK Airport");
//		airport.setMaxNumberOfPlanes(12125);
//		airport.setMaxNumberOfClients(10001);
//		airport.setLatitude(17.5);
//		airport.setLongitude(-73.789288);
//		airport.setCode("JFKF");
//		airport.setCity("New York");
//
//		assertThrows(ConstraintViolationException.class, () -> {
//			airportService.saveAirport(airport);
//		});
//	}
//
//	@Test
//	@Transactional
//	public void shouldNotInsertCodeWithNumbers() {
//
//		Airport airport = new Airport();
//		airport.setName("JFK Airport");
//		airport.setMaxNumberOfPlanes(12125);
//		airport.setMaxNumberOfClients(10001);
//		airport.setLatitude(17.5);
//		airport.setLongitude(-73.789288);
//		airport.setCode("JF4");
//		airport.setCity("New York");
//
//		assertThrows(ConstraintViolationException.class, () -> {
//			airportService.saveAirport(airport);
//		});
//
//	}
//
//	@Test
//	@Transactional
//	public void shouldNotInsertCodeWithLessThan3Letters() {
//
//		Airport airport = new Airport();
//		airport.setName("JFK Airport");
//		airport.setMaxNumberOfPlanes(12125);
//		airport.setMaxNumberOfClients(10001);
//		airport.setLatitude(17.5);
//		airport.setLongitude(-73.789288);
//		airport.setCode("JF");
//		airport.setCity("New York");
//
//		assertThrows(ConstraintViolationException.class, () -> {
//			airportService.saveAirport(airport);
//		});
//
//	}
//
//	@Test
//	@Transactional
//	public void shouldNotInsertCityWithNumbers() {
//		Airport airport = new Airport();
//		airport.setName("JFK Airport 1");
//		airport.setMaxNumberOfPlanes(12125);
//		airport.setMaxNumberOfClients(10001);
//		airport.setLatitude(17.5);
//		airport.setLongitude(-73.789288);
//		airport.setCode("JFK");
//		airport.setCity("New York 1");
//
//		assertThrows(ConstraintViolationException.class, () -> {
//			airportService.saveAirport(airport);
//		});
//	}

}
