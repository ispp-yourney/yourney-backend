
package com.yourney.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import com.yourney.model.Activity;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
import com.yourney.model.projection.ItineraryProjection;
import com.yourney.repository.ActivityRepository;
import com.yourney.repository.ItineraryRepository;
import com.yourney.security.model.User;


@SpringBootTest
class ItineraryServiceTests {

	private static final long	TEST_ACTIVITY_ID	= 1;
	private static final long	TEST_ACTIVITY_ID_2	= 2;
	private static final long	TEST_ACTIVITY_ID_3	= 3;
	private static final long	TEST_ACTIVITY_ID_4	= 1;
	private static final long	TEST_ITINERARY_ID	= 1;
	private static final long	TEST_ITINERARY_ID_2	= 2;
	private static final int 	TEST_ITINERARY_PAGE = 1;
	private static final int 	TEST_ITINERARY_SIZE = 10;
	private static final String TEST_ITINERARY_COUNTRY_1 = "España";
	private static final String TEST_ITINERARY_CITY_1 = "Sevilla";
	private static final Double TEST_ITINERARY_MAXBUDGET = 9000.;
	private static final Integer TEST_ITINERARY_MAXDAYS = 1000;
	private static final Double TEST_ITINERARY_LATITUDE = 60.0;
	private static final Double TEST_ITINERARY_LONGITUDE = 60.0;
	private static final String TEST_ITINERARY_NAME = "itinerary test 0";
	private static final String TEST_ITINERARY_USERNAME = "user1";
	
	@Autowired
	protected ItineraryService itineraryService;
	
	@MockBean
	private ActivityService activityService;

	@MockBean
	private ActivityRepository activityRepository;
	
	@MockBean
	private ItineraryRepository itineraryRepository;


	public Activity a1 = new Activity();
	public Activity a2 = new Activity(); 
	public Activity a3 = new Activity(); 
	public Activity a4 = new Activity();
	
	public Itinerary it1 = new Itinerary();
	public Itinerary it2 = new Itinerary();
	public Pageable pageable = PageRequest.of(TEST_ITINERARY_PAGE, TEST_ITINERARY_SIZE);
	
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
			    
			    this.it1.setId((long)ItineraryServiceTests.TEST_ITINERARY_ID);
			    this.it1.setName("itinerary test 0");
			    this.it1.setDescription("lorem ipsum");
			    this.it1.setStatus(StatusType.PUBLISHED);
			    this.it1.setBudget(10.);
			    this.it1.setEstimatedDays(2);
			    this.it1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
			    this.it1.setViews(0);
			    this.it1.setAuthor(auth1);
			    
			    
			    this.it2.setId((long)ItineraryServiceTests.TEST_ITINERARY_ID_2);
			    this.it2.setName("itinerary test 1");
			    this.it2.setDescription("lorem ipsum");
			    this.it2.setStatus(StatusType.PUBLISHED);
			    this.it2.setBudget(10.);
			    this.it2.setEstimatedDays(1);
			    this.it2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
			    this.it2.setViews(0);
			    this.it2.setAuthor(auth1);
			    
//			    doReturn(Optional.of(it1)).when(this.itineraryRepository).findById(ActivityServiceTests.TEST_ITINERARY_ID);
			    given(this.itineraryRepository.findById(ItineraryServiceTests.TEST_ITINERARY_ID)).willReturn(Optional.of(this.it1));
			    
				//Activity

				this.a1.setId(ItineraryServiceTests.TEST_ACTIVITY_ID);
				this.a1.setTitle("comienza el test: Giralda");
				this.a1.setDescription("lorem ipsum 0");
				this.a1.setDay(1);
				this.a1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
				this.a1.setItinerary(this.it1);
				Collection<Activity> activities = new ArrayList<>();
				activities.add(this.a1);
				
//				doReturn(Optional.of(this.activity)).when(this.activityRepository).findById(ActivityServiceTests.TEST_ACTIVITY_ID);
				given(this.activityRepository.findById(ItineraryServiceTests.TEST_ACTIVITY_ID)).willReturn(Optional.of(this.a1));


				this.a2.setId(ItineraryServiceTests.TEST_ACTIVITY_ID_2);
				this.a2.setTitle("sigue el test: Giralda");
				this.a2.setDescription("lorem ipsum 1");
				this.a2.setDay(2);
				this.a2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
				this.a2.setItinerary(this.it1);
				activities.add(this.a2);
				
				this.a3.setId(ItineraryServiceTests.TEST_ACTIVITY_ID_3);
				this.a3.setTitle("termina el test: Giralda");
				this.a3.setDescription("lorem ipsum 2");
				this.a3.setDay(2);
				this.a3.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
				this.a3.setItinerary(this.it1);
				activities.add(this.a3);
				
				this.a4.setId(ItineraryServiceTests.TEST_ACTIVITY_ID_4);
				this.a4.setTitle("comienza el test: Coliseo Romano");
				this.a4.setDescription("lorem ipsum 0");
				this.a4.setDay(1);
				this.a4.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
				this.a4.setItinerary(this.it2);
				Collection<Activity> activities2 = new ArrayList<>();
				activities2.add(this.a4);
				
				this.it1.setActivities(activities);
				this.it2.setActivities(activities2);
				
				
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
			    
			    this.a1.setLandmark(la1);
			    this.a2.setLandmark(la1);
			    this.a3.setLandmark(la1);
			    
			    Landmark la2 = new Landmark();
			    
			    la2.setCategory("category 2");
			    la2.setCity("Roma");
			    la2.setCountry("Italia");
			    la2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
			    la2.setDescription("lorem ipsum 2");
			    la2.setEmail("monumento2@email.com");
			    la2.setId((long) 2);
			    la2.setImage(null);
			    la2.setImageUrl(null);
			    la2.setInstagram(null);
			    la2.setLatitude(40.0);
			    la2.setLongitude(30.0);
			    la2.setName("Monumento 2");
			    la2.setPhone("+2 222222222");
			    la2.setPrice(0.0);
			    la2.setEndPromotionDate(LocalDateTime.of(2050, 10, 10, 10, 10, 10));
			    la2.setTwitter(null);
			    la2.setViews((long) 10);
			    la2.setWebsite(null);
			    
			    this.a4.setLandmark(la2);
			    
			    List<Itinerary> list1 = new ArrayList<>();
			    list1.add(this.it1);
			    list1.add(this.it2);
			    
			    
				ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
				
				List<ItineraryProjection> itineraries1 = new ArrayList<>();
				ItineraryProjection itp1 = factory.createProjection(ItineraryProjection.class, it1);
				itineraries1.add(itp1);
				Page<ItineraryProjection> itinerariesPage1 = new PageImpl<>(itineraries1);
				
				List<ItineraryProjection> itineraries2 = new ArrayList<>();
				ItineraryProjection itp2 = factory.createProjection(ItineraryProjection.class, it2);
				itineraries2.add(itp1);
				itineraries2.add(itp2);
				Page<ItineraryProjection> itinerariesPage2 = new PageImpl<>(itineraries2);
			    
			    given(this.itineraryRepository.findAll()).willReturn(list1);
			    given(this.itineraryRepository.searchByProperties(TEST_ITINERARY_COUNTRY_1, TEST_ITINERARY_CITY_1, TEST_ITINERARY_MAXBUDGET, TEST_ITINERARY_MAXDAYS, this.pageable)).willReturn(itinerariesPage1);
			    given(this.itineraryRepository.searchByDistance(TEST_ITINERARY_LATITUDE, TEST_ITINERARY_LONGITUDE, this.pageable)).willReturn(itinerariesPage2);
			    given(this.itineraryRepository.searchByName(this.pageable, TEST_ITINERARY_NAME)).willReturn(itinerariesPage1);
			    given(this.itineraryRepository.searchByUsername(this.pageable, TEST_ITINERARY_USERNAME)).willReturn(itinerariesPage1);
			    given(this.itineraryRepository.searchByCurrentUsername(this.pageable, TEST_ITINERARY_USERNAME)).willReturn(itinerariesPage1);
			    given(this.itineraryRepository.save(this.it1)).willReturn(this.it1);
			    given(this.itineraryRepository.existsById(TEST_ITINERARY_ID)).willReturn(true);
	}
	
	@Test
	void testFindById() {

		Optional<Itinerary> expected = this.itineraryService.findById(ItineraryServiceTests.TEST_ITINERARY_ID);

		assertTrue(expected.isPresent());
		assertSame(expected.get(), this.it1);
	}
	
	@Test
	void testFindAll() {

		Iterable<Itinerary> expected = this.itineraryService.findAll();
		List<Itinerary> result = new ArrayList<Itinerary>();
		expected.forEach(result::add);

		assertEquals(2,result.size());
		assertSame(result.get(0), this.it1);
		assertSame(result.get(1), this.it2);
	}
	
	@Test
	void testSearchByProperties() {
		Page<ItineraryProjection> expected = this.itineraryService.searchByProperties(TEST_ITINERARY_COUNTRY_1, TEST_ITINERARY_CITY_1, TEST_ITINERARY_MAXBUDGET, TEST_ITINERARY_MAXDAYS, pageable);

		assertEquals(1,expected.getNumberOfElements());
		assertSame(TEST_ITINERARY_ID, (long) expected.toList().get(0).getId());
	}
	
	@Test
	void testSearchByDistance() {
		Page<ItineraryProjection> expected = this.itineraryService.searchByDistance(TEST_ITINERARY_LATITUDE, TEST_ITINERARY_LONGITUDE, this.pageable);

		assertEquals(2,expected.getNumberOfElements());
		assertSame(TEST_ITINERARY_ID, (long) expected.toList().get(0).getId());
		assertSame(TEST_ITINERARY_ID_2, (long) expected.toList().get(1).getId());
	}
	
	@Test
	void testSearchByName() {
		Page<ItineraryProjection> expected = this.itineraryService.searchByName(this.pageable, TEST_ITINERARY_NAME);

		assertEquals(1,expected.getNumberOfElements());
		assertSame(TEST_ITINERARY_ID, (long) expected.toList().get(0).getId());
	}
	
	@Test
	void testSearchByUsername() {
		Page<ItineraryProjection> expected = this.itineraryService.searchByUsername(this.pageable, TEST_ITINERARY_USERNAME);

		assertEquals(1,expected.getNumberOfElements());
		assertSame(TEST_ITINERARY_ID,(long) expected.toList().get(0).getId());
	}
	
	@Test
	void testSearchByCurrentUsername() {
		Page<ItineraryProjection> expected = this.itineraryService.searchByCurrentUsername(this.pageable, TEST_ITINERARY_USERNAME);

		assertEquals(1,expected.getNumberOfElements());
		assertSame(TEST_ITINERARY_ID,(long) expected.toList().get(0).getId());
	}
	
	@Test
	void testSave() {
		Itinerary expected = this.itineraryService.save(this.it1);

		assertSame(expected, this.it1);
	}
	
	@Test
	void testExistsById() {
		Boolean expected = this.itineraryService.existsById(TEST_ITINERARY_ID);

		assertTrue(expected);
	}

}
