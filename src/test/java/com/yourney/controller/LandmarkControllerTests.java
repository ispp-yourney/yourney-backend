package com.yourney.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.yourney.model.Activity;
import com.yourney.model.Image;
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
	private static final int TEST_LANDMARK_ID_NOT_FOUND = 4;
	private static final int TEST_LANDMARK_ID2 = 2;
	private static final int TEST_LANDMARK_ID3 = 3;
	private static final int TEST_LANDMARK_ID4 = 4;	
	private static final int TEST_IMAGE_ID = 78;
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
	    l2.setId((long)TEST_LANDMARK_ID2);
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

	    Landmark l4 = new Landmark();
	    l4.setId((long)TEST_LANDMARK_ID4);
	    l4.setName("Landmark 4 name");
	    l4.setDescription("Landmark 4 desc");
	    l4.setPrice(0.);
	    l4.setCountry("Italia");
	    l4.setCity("Roma");
	    l4.setLatitude(37.38618100597202);
	    l4.setLongitude(-5.992615925346369);
	    l4.setEndPromotionDate(LocalDateTime.of(2025, 12, 10, 8, 6, 4));
	    l4.setEmail("coliseo@email.com");
	    l4.setInstagram(null);
	    l4.setPhone("123456789");
	    l4.setTwitter(null);
	    l4.setWebsite(null);
	    l4.setCategory("Monumento histórico");
	    l4.setViews((long)0);
	    l4.setImage(null);

		Activity activity_l4 = new Activity(); 
		activity_l4.setId(1);
		activity_l4.setTitle("termina el test: Giralda");
		activity_l4.setDescription("lorem ipsum 1");
		activity_l4.setDay(2);
		activity_l4.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		activity_l4.setLandmark(l4);

	    Landmark landmarkCreado = new Landmark();
	    landmarkCreado.setId((long)TEST_LANDMARK_ID3);
	    landmarkCreado.setCategory(null);
	    landmarkCreado.setCity("Sevilla");
	    landmarkCreado.setCountry("Spain");
	    landmarkCreado.setDescription("description test");
	    landmarkCreado.setEmail("test@gmail.com");
	    landmarkCreado.setInstagram("https://instagram.com/testlandmark");
	    landmarkCreado.setLatitude(0.);
	    landmarkCreado.setLongitude(0.);
	    landmarkCreado.setName("Landmark test");
	    landmarkCreado.setTwitter("https://twitter.com/testlandmark");
	    landmarkCreado.setPhone("+1 3234645145");
	    landmarkCreado.setPrice(0.);
	    landmarkCreado.setWebsite("https://www.testlandmark.com/");
	    
	    
	    //Images
	    Image i1 = new Image();
	    i1.setId(TEST_LANDMARK_ID);
	    i1.setName("Image test landmark");
	    i1.setImageUrl("https://elviajista.com/wp-content/uploads/2020/06/habanacuba-730x487.jpg");
	    

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
	    

		given(this.landmarkService.existsActivityByLandmarkId((long)LandmarkControllerTests.TEST_LANDMARK_ID4)).willReturn(true);
		given(this.landmarkService.findById((long)LandmarkControllerTests.TEST_LANDMARK_ID4)).willReturn(Optional.of(l4));
	    given(this.landmarkService.findById((long)LandmarkControllerTests.TEST_LANDMARK_ID)).willReturn(Optional.of(l1));
	    given(this.imageService.findById((long)LandmarkControllerTests.TEST_IMAGE_ID)).willReturn(Optional.of(i1));
	    given(this.landmarkService.findAllCountries()).willReturn(countryList);
	    given(this.landmarkService.findCitiesByCountry(TEST_LANDMARK_COUNTRY)).willReturn(cityByCountryList);
	    given(this.landmarkService.findAllCities()).willReturn(cityList);
	    given(this.landmarkService.searchByProperties("%" + TEST_LANDMARK_COUNTRY + "%", "%" + TEST_LANDMARK_CITY + "%","%" + TEST_LANDMARK_NAME + "%", TEST_LANDMARK_SIZE, pageable)).willReturn(landmarksPage1);
	    doReturn(landmarkCreado).when(this.landmarkService).save(any());
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
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testCreateLandmark() throws Exception {
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("category", null);
		activityJSON.put("city", "Sevilla");
		activityJSON.put("country", "Spain");
		activityJSON.put("description", "description test");
		activityJSON.put("email", "test@gmail.com");
		activityJSON.put("country", "Spain");
		activityJSON.put("instagram", "https://instagram.com/testlandmark");
		activityJSON.put("latitude", 0);
		activityJSON.put("longitude", 0);
		activityJSON.put("name", "Landmark test");
		activityJSON.put("twitter", "https://twitter.com/testlandmark");
		activityJSON.put("phone", "+1 3234645145");
		activityJSON.put("price", 0);
		activityJSON.put("website", "https://www.testlandmark.com/");
		
		this.mockMvc.perform(post("/landmark/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))

//		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(TEST_LANDMARK_ID3)))
        .andExpect(jsonPath("$.city", is("Sevilla")))
        .andExpect(jsonPath("$.country", is("Spain")))
        .andExpect(jsonPath("$.email", is("test@gmail.com")))
        .andExpect(jsonPath("$.latitude", is(0.)))
        .andExpect(jsonPath("$.longitude", is(0.)))
        .andExpect(jsonPath("$.name", is("Landmark test")))
        .andExpect(jsonPath("$.instagram", is("https://instagram.com/testlandmark")))
        .andExpect(jsonPath("$.description", is("description test")))
        .andExpect(jsonPath("$.price", is(0.)))
        .andExpect(jsonPath("$.twitter", is("https://twitter.com/testlandmark")))
        .andExpect(jsonPath("$.website", is("https://www.testlandmark.com/")))
        .andExpect(jsonPath("$.phone", is("+1 3234645145")));
	}
	
	@Test
	void testCreateLandmarkNotRegistered() throws Exception {
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("category", null);
		activityJSON.put("city", "Sevilla");
		activityJSON.put("country", "Spain");
		activityJSON.put("description", "description test");
		activityJSON.put("email", "test@gmail.com");
		activityJSON.put("country", "Spain");
		activityJSON.put("instagram", "https://instagram.com/testlandmark");
		activityJSON.put("latitude", 0);
		activityJSON.put("longitude", 0);
		activityJSON.put("name", "Landmark test");
		activityJSON.put("twitter", "https://twitter.com/testlandmark");
		activityJSON.put("phone", "+1 3234645145");
		activityJSON.put("price", 0);
		activityJSON.put("website", "https://www.testlandmark.com/");
		
		this.mockMvc.perform(post("/landmark/create")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(jsonPath("$.text", is("El usuario no tiene permiso para crear POI sin registrarse.")));
		
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testUpdateLandmark() throws Exception {
		JSONObject landmarkJSON = new JSONObject();
		
		landmarkJSON.put("category", null);
		landmarkJSON.put("city", "Sevilla");
		landmarkJSON.put("country", "Spain");
		landmarkJSON.put("description", "description test");
		landmarkJSON.put("email", "test@gmail.com");
		landmarkJSON.put("country", "Spain");
		landmarkJSON.put("instagram", "https://instagram.com/testlandmark");
		landmarkJSON.put("latitude", 0);
		landmarkJSON.put("longitude", 0);
		landmarkJSON.put("name", "Landmark test");
		landmarkJSON.put("twitter", "https://twitter.com/testlandmark");
		landmarkJSON.put("phone", "+1 3234645145");
		landmarkJSON.put("price", 0);
		landmarkJSON.put("website", "https://www.testlandmark.com/");
		landmarkJSON.put("id", TEST_LANDMARK_ID);
		
		this.mockMvc.perform(put("/landmark/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(landmarkJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(TEST_LANDMARK_ID3)))
        .andExpect(jsonPath("$.city", is("Sevilla")))
        .andExpect(jsonPath("$.country", is("Spain")))
        .andExpect(jsonPath("$.email", is("test@gmail.com")))
        .andExpect(jsonPath("$.latitude", is(0.)))
        .andExpect(jsonPath("$.longitude", is(0.)))
        .andExpect(jsonPath("$.name", is("Landmark test")))
        .andExpect(jsonPath("$.instagram", is("https://instagram.com/testlandmark")))
        .andExpect(jsonPath("$.description", is("description test")))
        .andExpect(jsonPath("$.price", is(0.)))
        .andExpect(jsonPath("$.twitter", is("https://twitter.com/testlandmark")))
        .andExpect(jsonPath("$.website", is("https://www.testlandmark.com/")))
        .andExpect(jsonPath("$.phone", is("+1 3234645145")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testUpdateLandmarkNotFound() throws Exception {
		JSONObject landmarkJSON = new JSONObject();
		
		landmarkJSON.put("category", null);
		landmarkJSON.put("city", "Sevilla");
		landmarkJSON.put("country", "Spain");
		landmarkJSON.put("description", "description test");
		landmarkJSON.put("email", "test@gmail.com");
		landmarkJSON.put("country", "Spain");
		landmarkJSON.put("instagram", "https://instagram.com/testlandmark");
		landmarkJSON.put("latitude", 0);
		landmarkJSON.put("longitude", 0);
		landmarkJSON.put("name", "Landmark test");
		landmarkJSON.put("twitter", "https://twitter.com/testlandmark");
		landmarkJSON.put("phone", "+1 3234645145");
		landmarkJSON.put("price", 0);
		landmarkJSON.put("website", "https://www.testlandmark.com/");
		landmarkJSON.put("id", TEST_LANDMARK_ID_NOT_FOUND);
		
		this.mockMvc.perform(put("/landmark/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(landmarkJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))

//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("No existe el POI indicado")));
        
	}
	
	@Test
	void testUpdateLandmarkNotRegistered() throws Exception {
		JSONObject landmarkJSON = new JSONObject();
		
		landmarkJSON.put("category", null);
		landmarkJSON.put("city", "Sevilla");
		landmarkJSON.put("country", "Spain");
		landmarkJSON.put("description", "description test");
		landmarkJSON.put("email", "test@gmail.com");
		landmarkJSON.put("country", "Spain");
		landmarkJSON.put("instagram", "https://instagram.com/testlandmark");
		landmarkJSON.put("latitude", 0);
		landmarkJSON.put("longitude", 0);
		landmarkJSON.put("name", "Landmark test");
		landmarkJSON.put("twitter", "https://twitter.com/testlandmark");
		landmarkJSON.put("phone", "+1 3234645145");
		landmarkJSON.put("price", 0);
		landmarkJSON.put("website", "https://www.testlandmark.com/");
		landmarkJSON.put("id", TEST_LANDMARK_ID);
		
		this.mockMvc.perform(put("/landmark/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(landmarkJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("El usuario no tiene permiso de modificación sin registrarse.")));
        
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testDeleteItinerary() throws Exception {
		
		this.mockMvc.perform(delete("/landmark/delete/{id}", TEST_LANDMARK_ID))
		
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

//		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El punto de interés ha sido eliminado correctamente.")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testDeleteItineraryNotFound() throws Exception {
		
		this.mockMvc.perform(delete("/landmark/delete/{id}", TEST_LANDMARK_ID_NOT_FOUND))
		
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

//		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El punto de interés que intenta eliminar no existe.")));
	}
	
	@Test
	void testDeleteItineraryNotRegistered() throws Exception {
		
		this.mockMvc.perform(delete("/landmark/delete/{id}", TEST_LANDMARK_ID))
		
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

//		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El usuario no tiene permiso de eliminar sin registrarse.")));
	}

	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testDeleteLandmarkWithActivity() throws Exception {
		
		this.mockMvc.perform(delete("/landmark/delete/{id}", TEST_LANDMARK_ID4))
		
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        
//		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El punto de interés se encuentra asociado con al menos una actividad.")));
	}
	
}