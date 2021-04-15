package com.yourney.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.yourney.model.Image;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
import com.yourney.security.model.User;
import com.yourney.service.ImageService;
import com.yourney.service.ItineraryService;
import com.yourney.service.LandmarkService;


@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerTests {
	private static final int TEST_ITINERARY_ID = 1;
	private static final int TEST_ITINERARY_ID2 = 2;
	private static final int TEST_ITINERARY_ID3 = 3;
	private static final int TEST_LANDMARK_ID = 1;
	private static final int TEST_LANDMARK_ID2 = 2;
	private static final int TEST_LANDMARK_ID3 = 3;
	private static final int TEST_IMAGE_ID = 1;
	private static final int TEST_INEXISTENT_ID = 99;
	private static final int TEST_DEFAULT_IMAGE_ID = 78;
	
	@MockBean
	protected LandmarkService landmarkService;

	@MockBean
	protected ItineraryService itineraryService;

	@Autowired
	protected ImageController imageController;
	
	@MockBean
	protected ImageService imageService;

	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		//Images
		Image i1 = new Image();
		i1.setId(TEST_IMAGE_ID);
		i1.setName("Image test landmark");
		i1.setImageUrl("https://elviajista.com/wp-content/uploads/2020/06/habanacuba-730x487.jpg");
		i1.setCloudinaryId("1");

		Image defaultImage = new Image();
		defaultImage.setId(TEST_DEFAULT_IMAGE_ID);
		defaultImage.setName("Image test landmark");
		defaultImage.setImageUrl("https://elviajista.com/wp-content/uploads/2020/06/habanacuba-730x487.jpg");
		defaultImage.setCloudinaryId("1");

		// USUARIOS

		User us1 = new User();

		us1.setId((long)1);
		us1.setEmail("testuser@email.com");
		us1.setFirstName("Name 1");
		us1.setLastName("Surname 1");
		us1.setPassword("user1");
		us1.setUsername("user1");
		us1.setPlan(0);
		
		// ITINERARIOS
		
		Itinerary it1 = new Itinerary();
		
		it1.setId((long)TEST_ITINERARY_ID);
		it1.setName("itinerary test 1");
		it1.setDescription("lorem ipsum 1");
		it1.setStatus(StatusType.PUBLISHED);
		it1.setBudget(10.);
		it1.setEstimatedDays(2);
		it1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		it1.setViews(0);
		it1.setAuthor(us1);
		
		doReturn(it1).when(this.itineraryService).save(any());

		Itinerary it2 = new Itinerary();
		
		it2.setId((long)TEST_ITINERARY_ID2);
		it2.setName("itinerary test 1");
		it2.setDescription("lorem ipsum 1");
		it2.setStatus(StatusType.PUBLISHED);
		it2.setBudget(10.);
		it2.setEstimatedDays(2);
		it2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		it2.setViews(0);
		it2.setAuthor(us1);
		it2.setImage(i1);

		Itinerary it3 = new Itinerary();
		
		it3.setId((long)TEST_ITINERARY_ID3);
		it3.setName("itinerary test 1");
		it3.setDescription("lorem ipsum 1");
		it3.setStatus(StatusType.PUBLISHED);
		it3.setBudget(10.);
		it3.setEstimatedDays(2);
		it3.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		it3.setViews(0);
		it3.setAuthor(us1);
		it3.setImage(defaultImage);
		
		doReturn(it1).when(this.itineraryService).save(any());

		// ITINERARIOS
		
		Landmark l1 = new Landmark();
		
		l1.setId((long)TEST_LANDMARK_ID);
		l1.setName("itinerary test 1");
		l1.setDescription("lorem ipsum 1");
		l1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		l1.setViews(0L);

		Landmark l2 = new Landmark();
		
		l2.setId((long)TEST_LANDMARK_ID2);
		l2.setName("itinerary test 1");
		l2.setDescription("lorem ipsum 1");
		l2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		l2.setViews(0L);
		l2.setImage(i1);

		Landmark l3 = new Landmark();
		
		l3.setId((long)TEST_LANDMARK_ID3);
		l3.setName("itinerary test 1");
		l3.setDescription("lorem ipsum 1");
		l3.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		l3.setViews(0L);
		l3.setImage(defaultImage);
		
		doReturn(it1).when(this.itineraryService).save(any());

		given(this.landmarkService.findById((long)TEST_LANDMARK_ID3)).willReturn(Optional.of(l3));
		given(this.landmarkService.findById((long)TEST_LANDMARK_ID2)).willReturn(Optional.of(l2));
		given(this.landmarkService.findById((long)TEST_LANDMARK_ID)).willReturn(Optional.of(l1));
		given(this.itineraryService.findById((long)TEST_ITINERARY_ID3)).willReturn(Optional.of(it3));
		given(this.itineraryService.findById((long)TEST_ITINERARY_ID2)).willReturn(Optional.of(it2));
		given(this.itineraryService.findById((long)TEST_ITINERARY_ID)).willReturn(Optional.of(it1));
		given(this.imageService.findById((long)TEST_IMAGE_ID)).willReturn(Optional.of(i1));
		given(this.imageService.findById((long)TEST_DEFAULT_IMAGE_ID)).willReturn(Optional.of(defaultImage));
		}
	
	@Test
	void testAnonymousDeleteForItinerary() throws Exception {
		
		this.mockMvc.perform(delete("/image/deleteForItinerary/{itineraryId}", TEST_ITINERARY_ID))
		
		// Validate the response code and content type
		.andExpect(status().isForbidden())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El usuario no tiene permiso de eliminación sin registrarse.")));
		}

	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testDeleteForInexistentItinerary() throws Exception {
			
		this.mockMvc.perform(delete("/image/deleteForItinerary/{itineraryId}", TEST_INEXISTENT_ID))
		
		// Validate the response code and content type
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El itinerario sobre el que intenta eliminar la imagen no existe")));
	}

	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testDeleteForItineraryWithNoImage() throws Exception {
			
		this.mockMvc.perform(delete("/image/deleteForItinerary/{itineraryId}", TEST_ITINERARY_ID))
		
		// Validate the response code and content type
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El itinerario indicado no contiene ninguna imagen")));
	}

	@Test
	@WithMockUser(username = "user99", password = "user99")
	void testDeleteForItineraryNotPropietary() throws Exception {
			
		this.mockMvc.perform(delete("/image/deleteForItinerary/{itineraryId}", TEST_ITINERARY_ID2))
		
		// Validate the response code and content type
		.andExpect(status().isForbidden())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("No es posible eliminar imágenes a un itinerario del que no es dueño.")));
	}


	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testDeleteForItineraryWithDefaultImage() throws Exception {
			
		this.mockMvc.perform(delete("/image/deleteForItinerary/{itineraryId}", TEST_ITINERARY_ID3))
		
		// Validate the response code and content type
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El itinerario no tiene ninguna foto asociada.")));
	}

	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testDeleteForItinerary() throws Exception {
			
		this.mockMvc.perform(delete("/image/deleteForItinerary/{itineraryId}", TEST_ITINERARY_ID2))
		
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("Imagen eliminada correctamente")));
	}

	
@Test
void testAnonymousDeleteForLandmark() throws Exception {
	
	this.mockMvc.perform(delete("/image/deleteForLandmark/{landmarkId}", TEST_LANDMARK_ID))
	
	// Validate the response code and content type
	.andExpect(status().isForbidden())
	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	

	// Validate the returned fields
	.andExpect(jsonPath("$.text", is("El usuario no tiene permiso de eliminación sin registrarse.")));
	}

@Test
@WithMockUser(username = "user1", password = "user1")
void testDeleteForInexistentLandmark() throws Exception {
		
	this.mockMvc.perform(delete("/image/deleteForLandmark/{landmarkId}", TEST_INEXISTENT_ID))
	
	// Validate the response code and content type
	.andExpect(status().isNotFound())
	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	

	// Validate the returned fields
	.andExpect(jsonPath("$.text", is("El itinerario sobre el que intenta eliminar la imagen no existe")));
}

@Test
@WithMockUser(username = "user1", password = "user1")
void testDeleteForLandmarkWithNoImage() throws Exception {
		
	this.mockMvc.perform(delete("/image/deleteForLandmark/{landmarkId}", TEST_LANDMARK_ID))
	
	// Validate the response code and content type
	.andExpect(status().isNotFound())
	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	

	// Validate the returned fields
	.andExpect(jsonPath("$.text", is("El POI indicado no contiene ninguna imagen")));
}

@Test
@WithMockUser(username = "user1", password = "user1")
void testDeleteForLandmarkWithDefaultImage() throws Exception {
		
	this.mockMvc.perform(delete("/image/deleteForLandmark/{landmarkId}", TEST_LANDMARK_ID3))
	
	// Validate the response code and content type
	.andExpect(status().isNotFound())
	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	

	// Validate the returned fields
	.andExpect(jsonPath("$.text", is("El POI no tiene ninguna foto asociada.")));
}

@Test
@WithMockUser(username = "user1", password = "user1")
void testDeleteForLandmark() throws Exception {
		
	this.mockMvc.perform(delete("/image/deleteForLandmark/{landmarkId}", TEST_LANDMARK_ID2))
	
	// Validate the response code and content type
	.andExpect(status().isOk())
	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	

	// Validate the returned fields
	.andExpect(jsonPath("$.text", is("Imagen eliminada correctamente")));
}
/*
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
	void testUpgradeNegativeDaysLandmark() throws Exception {
		
		this.mockMvc.perform(get("/landmark/upgrade?landmarkId={landmarkId}&subscriptionDays={subscriptionDays}", TEST_LANDMARK_ID4, -33))
		
		// Validate the response code and content type
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("No puede subscribirse a días negativos o nulos")));
	}
*/
}