
package com.yourney.service;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.yourney.model.Activity;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
import com.yourney.repository.ActivityRepository;
import com.yourney.repository.ItineraryRepository;
import com.yourney.security.model.User;


@SpringBootTest
class ActivityServiceTests {

	private static final long	TEST_ACTIVITY_ID	= 1;
	private static final long	TEST_ACTIVITY_ID_2	= 2;
	private static final long	TEST_ACTIVITY_ID_3	= 3;
	private static final long	TEST_ACTIVITY_ID_4	= 1;
	private static final long	TEST_ITINERARY_ID	= 1;
	private static final long	TEST_ITINERARY_ID_2	= 2;
	
	@Autowired
	protected ActivityService activityService;
	
	@MockBean
	private ItineraryService itineraryService;

	@MockBean
	private ActivityRepository activityRepository;
	
	@MockBean
	private ItineraryRepository itineraryRepository;


	public Activity a1 = new Activity();
	public Activity a2 = new Activity(); 
	public Activity a3 = new Activity(); 
	public Activity a4 = new Activity(); 
	
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
	    
	    Itinerary it2 = new Itinerary();
	    
	    it2.setId((long)ActivityServiceTests.TEST_ITINERARY_ID_2);
	    it2.setName("itinerary test 1");
	    it2.setDescription("lorem ipsum");
	    it2.setStatus(StatusType.PUBLISHED);
	    it2.setBudget(10.);
	    it2.setEstimatedDays(1);
	    it2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it2.setViews(0);
	    it2.setAuthor(auth1);
	    
//	    doReturn(Optional.of(it1)).when(this.itineraryRepository).findById(ActivityServiceTests.TEST_ITINERARY_ID);
	    given(this.itineraryRepository.findById(ActivityServiceTests.TEST_ITINERARY_ID)).willReturn(Optional.of(it1));
	    
		//Activity

		this.a1.setId(ActivityServiceTests.TEST_ACTIVITY_ID);
		this.a1.setTitle("comienza el test: Giralda");
		this.a1.setDescription("lorem ipsum 0");
		this.a1.setDay(1);
		this.a1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		this.a1.setItinerary(it1);
		Collection<Activity> activities = new ArrayList<>();
		activities.add(this.a1);
		
//		doReturn(Optional.of(this.activity)).when(this.activityRepository).findById(ActivityServiceTests.TEST_ACTIVITY_ID);
		given(this.activityRepository.findById(ActivityServiceTests.TEST_ACTIVITY_ID)).willReturn(Optional.of(this.a1));


		this.a2.setId(ActivityServiceTests.TEST_ACTIVITY_ID_2);
		this.a2.setTitle("sigue el test: Giralda");
		this.a2.setDescription("lorem ipsum 1");
		this.a2.setDay(2);
		this.a2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		this.a2.setItinerary(it1);
		activities.add(this.a2);
		
		this.a3.setId(ActivityServiceTests.TEST_ACTIVITY_ID_3);
		this.a3.setTitle("termina el test: Giralda");
		this.a3.setDescription("lorem ipsum 2");
		this.a3.setDay(2);
		this.a3.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		this.a3.setItinerary(it1);
		activities.add(this.a3);
		
		this.a4.setId(ActivityServiceTests.TEST_ACTIVITY_ID_4);
		this.a4.setTitle("comienza el test: Giralda");
		this.a4.setDescription("lorem ipsum 0");
		this.a4.setDay(1);
		this.a4.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		this.a4.setItinerary(it2);
		Collection<Activity> activities2 = new ArrayList<>();
		activities2.add(this.a4);
		
		it1.setActivities(activities);
		it2.setActivities(activities2);
		
		
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
	    
	    List<Activity> list1 = new ArrayList<>();
	    list1.add(this.a1);
	    list1.add(this.a2);
	    list1.add(this.a3);
	    list1.add(this.a4);
	    Iterable<Activity> iterable1 = list1;
	    
	    List<Activity> list2 = new ArrayList<>();
	    list2.add(this.a2);
	    list2.add(this.a3);
	    Iterable<Activity> iterable2 = list2;
	    
	    List<Activity> list3 = new ArrayList<>();
	    list3.add(this.a1);
	    list3.add(this.a2);
	    list3.add(this.a3);
	    Iterable<Activity> iterable3 = list3;
	    
	    given(this.activityRepository.findAll()).willReturn(iterable1);
	    given(this.activityRepository.save(this.a1)).willReturn(this.a1);
	    given(this.activityRepository.existsById(this.a1.getId())).willReturn(true);
	    given(this.activityRepository.findAllActivityProjectionsByDayAndItinerary(it1.getId(), 2)).willReturn(iterable2);
	    given(this.activityRepository.findActivityByItinerary(it1.getId())).willReturn(iterable3);

	}
	
	@Test
	void testFindById() {

		Optional<Activity> expected = this.activityService.findById(ActivityServiceTests.TEST_ACTIVITY_ID);

		assertTrue(expected.isPresent());
		assertSame(expected.get(), this.a1);
	}
	
	@Test
	void testFindAll() {

		Iterable<Activity> expected = this.activityService.findAll(); 
		List<Activity> result = new ArrayList<Activity>();
		expected.forEach(result::add);

		assertEquals(4,result.size());
		assertSame(result.get(0), this.a1);
		assertSame(result.get(1), this.a2);
		assertSame(result.get(2), this.a3);
		assertSame(result.get(3), this.a4);
	}
	
	@Test
	void testSave() {

		Activity expected = this.activityService.save(this.a1); 

		assertSame(expected, this.a1);
	}
	
	@Test
	void testExistsById() {

		Boolean expected = this.activityService.existsById(this.a1.getId()); 

		assertTrue(expected);
	}
	
	@Test
	void testFindAllActivityProjectionsByDayAndItinerary() {

		Iterable<Activity> expected = this.activityService.findAllActivityProjectionsByDayAndItinerary(ActivityServiceTests.TEST_ITINERARY_ID, 2); 
		List<Activity> result = new ArrayList<Activity>();
		expected.forEach(result::add);

		assertEquals(2,result.size());
		assertSame(result.get(0), this.a2);
		assertSame(result.get(1), this.a3);
	}
	
	@Test
	void testFindActivityByItinerary() {

		Iterable<Activity> expected = this.activityService.findActivityByItinerary(ActivityServiceTests.TEST_ITINERARY_ID); 
		List<Activity> result = new ArrayList<Activity>();
		expected.forEach(result::add);

		assertEquals(3,result.size());
		assertSame(result.get(0), this.a1);
		assertSame(result.get(1), this.a2);
		assertSame(result.get(2), this.a3);
	}
	
}
