
package com.yourney.service;

import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

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
import com.yourney.model.Landmark;
import com.yourney.model.projection.LandmarkProjection;
import com.yourney.repository.ActivityRepository;
import com.yourney.repository.ItineraryRepository;
import com.yourney.repository.LandmarkRepository;
import com.yourney.security.model.User;


@SpringBootTest
class LandmarkServiceTests {

	private static final long	TEST_ACTIVITY_ID_1	= 1;
	private static final long TEST_LANDMARK_ID1 = 1;
	private static final long TEST_LANDMARK_ID2 = 2;
	private static final long TEST_LANDMARK_ID_NOT_FOUND = 5;	
	private static final int TEST_LANDMARK_PAGE = 1;
	private static final int TEST_LANDMARK_SIZE = 10;
	
	@Autowired
	protected LandmarkService landmarkService;
	
	@MockBean
	private ItineraryService itineraryService;

	@MockBean
	private ActivityRepository activityRepository;
	
	@MockBean
	private LandmarkRepository landmarkRepository;
	
	@MockBean
	private ItineraryRepository itineraryRepository;

	public Landmark l1 = new Landmark();
	public Landmark l2 = new Landmark();
	Pageable pageable;
	Page<LandmarkProjection> landmarksPage1;
	
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

		// LADNMARKS
	    
	    
	    l1.setCategory("category 1");
	    l1.setCity("Sevilla");
	    l1.setCountry("España");
	    l1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    l1.setDescription("lorem ipsum 1");
	    l1.setEmail("monumento1@email.com");
	    l1.setId((long) TEST_LANDMARK_ID1);
	    l1.setImage(null);
	    l1.setImageUrl(null);
	    l1.setInstagram(null);
	    l1.setLatitude(60.0);
	    l1.setLongitude(60.0);
	    l1.setName("Monumento 1");
	    l1.setPhone("+1 111111111");
	    l1.setPrice(0.0);
	    l1.setEndPromotionDate(LocalDateTime.of(2050, 10, 10, 10, 10, 10));
	    l1.setTwitter(null);
	    l1.setViews((long) 10);
	    l1.setWebsite(null);
	    
	    
	    l2.setId((long)TEST_LANDMARK_ID2);
	    l2.setCategory(null);
	    l2.setCity("París");
	    l2.setCountry("Francia");
	    l2.setDescription("description test");
	    l2.setEmail("test@gmail.com");
	    l2.setInstagram("https://instagram.com/testlandmark");
	    l2.setLatitude(0.);
	    l2.setLongitude(0.);
	    l2.setName("Landmark test");
	    l2.setTwitter("https://twitter.com/testlandmark");
	    l2.setPhone("+1 3234645145");
	    l2.setPrice(0.);
	    l2.setWebsite("https://www.testlandmark.com/");
	    
	    //Activities
	    Activity activity_l4 = new Activity(); 
		activity_l4.setId(TEST_ACTIVITY_ID_1);
		activity_l4.setTitle("termina el test: Giralda");
		activity_l4.setDescription("lorem ipsum 1");
		activity_l4.setDay(2);
		activity_l4.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		activity_l4.setLandmark(l1);
		
		
		
	    Collection<String> countryList = new ArrayList<>();
	    countryList.add(l1.getCountry());
	    countryList.add(l2.getCountry());
	    
	    Collection<String> countryListWithItinerary = new ArrayList<>();
	    countryListWithItinerary.add(l1.getCountry());
	    
	    Collection<String> cityByCountryList = new ArrayList<>();
	    cityByCountryList.add(l1.getCity());
	    
	    Collection<String> cityList = new ArrayList<>();
	    cityList.add(l2.getCity());
	    cityList.add(l1.getCity());
	    
	    Collection<String> cityListWithItinerary = new ArrayList<>();
	    cityListWithItinerary.add(l1.getCity());
	    
	    Collection<Landmark> landmarks = new ArrayList<>();
	    landmarks.add(l1);
	    landmarks.add(l2);
	    
	    Pageable pageable = PageRequest.of(TEST_LANDMARK_PAGE, TEST_LANDMARK_SIZE);
		ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
		
		List<LandmarkProjection> landmarks1 = new ArrayList<>();
		LandmarkProjection lap1 = factory.createProjection(LandmarkProjection.class, l1);
		landmarks1.add(lap1);
		Page<LandmarkProjection> landmarksPage1 = new PageImpl<>(landmarks1);
		
		
		
		given(this.landmarkRepository.findById(TEST_LANDMARK_ID1)).willReturn(Optional.of(l1));
		given(this.landmarkRepository.findById(TEST_LANDMARK_ID_NOT_FOUND)).willReturn(Optional.empty());
		given(this.landmarkRepository.findAll()).willReturn(landmarks);
		given(this.landmarkRepository.existsActivityByLandmarkId(TEST_LANDMARK_ID1)).willReturn(true);
		given(this.landmarkRepository.existsActivityByLandmarkId(TEST_LANDMARK_ID_NOT_FOUND)).willReturn(false);
		given(this.landmarkRepository.searchByProperties(l1.getCountry(), l1.getCity(), l1.getName(), pageable)).willReturn(landmarksPage1);
	    doReturn(l1).when(this.landmarkRepository).save(any());
	    given(this.landmarkRepository.findAllCountries()).willReturn(countryList);
	    given(this.landmarkRepository.findManyCountriesWithItinerary()).willReturn(countryListWithItinerary);
	    given(this.landmarkRepository.findAllCities()).willReturn(cityList);
	    given(this.landmarkRepository.findManyCitiesWithItinerary()).willReturn(cityListWithItinerary);
	    given(this.landmarkRepository.findCitiesByCountry(l1.getCountry())).willReturn(cityByCountryList);
	    given(this.landmarkRepository.searchOrderedByViews(l1.getCountry(), l1.getCity(), pageable)).willReturn(landmarksPage1);
	    

	}
	
	
	@Test
	void testFindById() {

		Optional<Landmark> expected = this.landmarkService.findById(LandmarkServiceTests.TEST_LANDMARK_ID1);

		assertTrue(expected.isPresent());
		assertSame(expected.get(), this.l1);
	}
	
	@Test
	void testFindByIdNotFound() {

		Optional<Landmark> expected = this.landmarkService.findById(LandmarkServiceTests.TEST_LANDMARK_ID_NOT_FOUND);

		assertFalse(expected.isPresent());
	}
	
	@Test
	void testExistsActivityByLandmarkId() {

		Boolean expected = this.landmarkService.existsActivityByLandmarkId(LandmarkServiceTests.TEST_LANDMARK_ID1);

		assertSame(true, expected);
	}
	
	@Test
	void testExistsActivityByLandmarkIdNotFound() {

		Boolean expected = this.landmarkService.existsActivityByLandmarkId(LandmarkServiceTests.TEST_LANDMARK_ID_NOT_FOUND);

		assertSame(false, expected);
	}
	
	@Test
	void testFindAll() {

		Iterable<Landmark> expected = this.landmarkService.findAll(); 
		List<Landmark> result = new ArrayList<Landmark>();
		expected.forEach(result::add);

		assertEquals(2,result.size());
		assertSame(result.get(0), this.l1);
		assertSame(result.get(1), this.l2);
	}
	
	@Test
	void testFindAllCountries() {

		Iterable<String> expected = this.landmarkService.findAllCountries(false); 

		List<String> result = new ArrayList<String>();
		expected.forEach(result::add);
		
		assertEquals(2,result.size());
		assertSame("España", result.get(0));
		assertSame("Francia", result.get(1));
	}
	
	@Test
	void testFindAllCountriesWithItinerary() {

		Iterable<String> expected = this.landmarkService.findAllCountries(true); 

		List<String> result = new ArrayList<String>();
		expected.forEach(result::add);
		
		assertEquals(1,result.size());
		assertSame("España", result.get(0));
	}

	@Test
	void testFindCitiesByCountry() {

		Iterable<String> expected = this.landmarkService.findCitiesByCountry(l1.getCountry()); 

		List<String> result = new ArrayList<String>();
		expected.forEach(result::add);
		
		assertEquals(1,result.size());
		assertSame("Sevilla",result.get(0));
	}
	
	@Test
	void testFindAllCities() {

		Iterable<String> expected = this.landmarkService.findAllCities(false); 

		List<String> result = new ArrayList<String>();
		expected.forEach(result::add);
		
		assertEquals(2,result.size());
		assertSame("París",result.get(0));
		assertSame("Sevilla",result.get(1));
	}
	
	@Test
	void testFindAllCitiesWithItinerary() {

		Iterable<String> expected = this.landmarkService.findAllCities(true); 

		List<String> result = new ArrayList<String>();
		expected.forEach(result::add);
		
		assertEquals(1,result.size());
		assertSame("Sevilla",result.get(0));
	}

	
	@Test
	void testSave() {

		Landmark expected = this.landmarkService.save(this.l1); 

		assertSame(expected, this.l1);
	}

  @Test
  void testFindByProperties() {

      Iterable<LandmarkProjection> expected = this.landmarkService.searchByProperties("España", "Sevilla", "Monumento 1", PageRequest.of(TEST_LANDMARK_PAGE, TEST_LANDMARK_SIZE));
      List<LandmarkProjection> result = new ArrayList<LandmarkProjection>();
      expected.forEach(result::add);

      assertEquals(1,result.size());
      assertSame("Sevilla",result.get(0).getCity());
      assertSame("España",result.get(0).getCountry());

  }

  @Test
  void testFindOrderedByViews() {

      Iterable<LandmarkProjection> expected = this.landmarkService.searchOrderedByViews("España", "Sevilla", PageRequest.of(TEST_LANDMARK_PAGE, TEST_LANDMARK_SIZE));
      List<LandmarkProjection> result = new ArrayList<LandmarkProjection>();
      expected.forEach(result::add);

      assertEquals(1,result.size());
      assertSame("Sevilla",result.get(0).getCity());
      assertSame("España",result.get(0).getCountry());

  }
	
}
