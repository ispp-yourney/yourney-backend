package com.yourney.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.yourney.model.Image;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;
import com.yourney.service.CloudinaryService;
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

	@MockBean
	protected UserService userService;

	@Autowired
	protected ImageController imageController;
	
	@MockBean
	protected ImageService imageService;

	@MockBean
	protected CloudinaryService cloudinaryService;

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

		User us2 = new User();
		us2.setId((long)2);
		us2.setEmail("testuser2@email.com");
		us2.setFirstName("Name 2");
		us2.setLastName("Surname 2");
		us2.setPassword("user2");
		us2.setUsername("user2");
		us2.setPlan(0);
		us2.setImage(i1);

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

		Map cloudinaryUploadResultMap = new HashMap<>();
		cloudinaryUploadResultMap.put("original_filename", "image");
		cloudinaryUploadResultMap.put("url", "https://github.com/ispp-yourney/yourney-backend/tree/feature-issue%2372");
		cloudinaryUploadResultMap.put("public_id", "1");

		try {
			given(this.cloudinaryService.upload(any())).willReturn(cloudinaryUploadResultMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		given(this.userService.getCurrentUsername()).willReturn("anonymousUser");
		given(this.userService.getByUsername("user1")).willReturn(Optional.of(us1));
		given(this.userService.getByUsername("user2")).willReturn(Optional.of(us2));
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
		given(this.userService.getCurrentUsername()).willReturn("user1");
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
		given(this.userService.getCurrentUsername()).willReturn("user1");
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
		given(this.userService.getCurrentUsername()).willReturn("user99");
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
		given(this.userService.getCurrentUsername()).willReturn("user1");
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
		given(this.userService.getCurrentUsername()).willReturn("user1");
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
	given(this.userService.getCurrentUsername()).willReturn("user1");
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
	given(this.userService.getCurrentUsername()).willReturn("user1");	
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
	given(this.userService.getCurrentUsername()).willReturn("user1");
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
	given(this.userService.getCurrentUsername()).willReturn("user1");
	this.mockMvc.perform(delete("/image/deleteForLandmark/{landmarkId}", TEST_LANDMARK_ID2))
	
	// Validate the response code and content type
	.andExpect(status().isOk())
	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	

	// Validate the returned fields
	.andExpect(jsonPath("$.text", is("Imagen eliminada correctamente")));
}

@Test
void testAnonymousDeleteForUser() throws Exception {
	
	this.mockMvc.perform(delete("/image/deleteForUser"))
	
	// Validate the response code and content type
	.andExpect(status().isForbidden())
	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	

	// Validate the returned fields
	.andExpect(jsonPath("$.text", is("El usuario no tiene permiso de eliminación sin registrarse.")));
	}

@Test
@WithMockUser(username = "user99", password = "user99")
void testNoUserDeleteForUser() throws Exception {
	given(this.userService.getCurrentUsername()).willReturn("user99");
	this.mockMvc.perform(delete("/image/deleteForUser"))
	
	// Validate the response code and content type
	.andExpect(status().isNotFound())
	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	

	// Validate the returned fields
	.andExpect(jsonPath("$.text", is("El usuario al que desea asignar la imagen no existe.")));
	}

	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testNoImageFromUserDeleteForUser() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user1");
		this.mockMvc.perform(delete("/image/deleteForUser"))
		
		// Validate the response code and content type
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El usuario no tiene ninguna foto asociada.")));
	}

	@Test
	@WithMockUser(username = "user2", password = "user2")
	void testImageDeleteForUser() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user2");
		this.mockMvc.perform(delete("/image/deleteForUser"))
		
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("Imagen eliminada correctamente")));
	}


	@Test
	void testAnonymousUploadForUser() throws Exception {
		
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "text/plain", "an image".getBytes());

		this.mockMvc.perform(multipart("/image/uploadForUser")
		.file("multipartFile",image.getBytes()))
		
		// Validate the response code and content type
		.andExpect(status().isForbidden())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El usuario no tiene permiso de creación sin registrarse.")));
	}

	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testWrongUploadForUser() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user1");
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "text/plain", new byte[]{});

		this.mockMvc.perform(multipart("/image/uploadForUser")
		.file("multipartFile",image.getBytes()))
		
		// Validate the response code and content type
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("La imagen no es válida")));
	}

	@Test
	@WithMockUser(username = "user2", password = "user2")
	void testUploadForUser() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user2");
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "image/jpeg", Base64.getDecoder().decode("/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAMCAgoICAgICA0ICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAoICAgICgoKCAcNDQoIDQcICggBAwQEBgUGCgYGCg0NCA0NDQgNDQ0NDQ0ICAgICA0ICAgICAgNCAgICAgICAgICAgICAgICAgICAgICAgICAgICP/AABEIAUACAAMBEQACEQEDEQH/xAAcAAADAQEBAQEBAAAAAAAAAAABAgMEAAYHBQj/xAAdEAEAAwEBAQEBAQAAAAAAAAAAAQIDBBMUEhFx/8QAGwEAAwEBAQEBAAAAAAAAAAAAAAECAwQFBgf/xAAeEQEBAQEBAQADAQEAAAAAAAAAARECEhMDITFRQf/aAAwDAQACEQMRAD8A1Vq91+s09ammq0qqMqpFVsulIq1lc3UUiq5WPQRVrL+3N078tI5ug/DWOXuFtRrHH0W2bWVx9lmrWOLt1ato4uz1o3ji7PWq5XB+RSKN44u1IhtK4e1IhpHF2P8AGscXZoq15cfcCatp/HJ1CzVpHLSTVrGHcRvDWML+0rQ1jCp3hUY3+pXhpEVCYXGSV6riKjMLQSyoCTU0kmqgSQAmAqUPyFhNQqDFQ25oxVLWG/Ja6YMUTa6ODVonXZyP4S7OKP4Rrv4D8Fa9D8ddNEvR4J+U16H4yzml6P46EUD0fx08UDv4pozLXbxVK5la7OarWibXVzV6Zp1081ozoltK1Z0ZWtJWzKiLW0rXnVFVrZjVnaqNWcM6rWrOrOnrTSrO02iEhWoD5RSr8x17NUii2VPSpoqn4VGPSsVayufpStWkrHofw1lcvcLNV65enTRrK5uwmjaOPss0axydF/DSVx9uijbmuHuHrVvHB2eKtJXF2pWrWVw/kNSraVxdw9at3D3DRVpHH0P5a8uPqBNW8cfRJo0jn6JaraObpK9WjnqN6tNY1K9WkZVO1VyskL1VEVK0NIzqNqqiKnaFEWYVpYWanpF/gBZqDhZgauOmBq4f8J1pP46tRreHiidbcjFE66uab8J12cmiiK7OTfhOu3iujNNr0Px0PMnocUvmVr0eKWc069D8dCMk2vQ4pozTrv4p60L07uKpXItdfNVpmm9OrmrUon06ea0Uqm1vK1Z5s2srVlmn00a86s9aStWVWdq5WrOjO1UrXlVFqmikJUtSEhWsJ0Pltc35nr2rFK0XKzPGamdUiitY9KVqqVz9KVo0jDo0ZtpXP262bSOTos5tJXN2E0ayuPp05t+a4+iTRpHH07za8uLuGrm3lcPakUayuLs9c2srh7h60ayuLuD5t5+3D3DfhpL+3J1Hfltrj6gTRvzXH3CTVq5uk7Vaxz9RK1Wsc9RvVpGHSM1aMbE5hbNG9VxFiV6qlZJXorU2I2qrU4SanpF/hglqjQ7+AB+TXD1qnVuipa0h60K1tDxROtuTRRNrp5P5p11cGrmi12cD5p138U0Zlru4DyTr0OKE4o9PQ/HQnJGu/ilnItehxRjMvT0OKauSbXbxVa5J9OviqUzLXXzV6UTa6ea0Z0LW8rRnRFrfWnLNnv8Ai5WvPJna1lasqItW00hNVGikIq4vnVNptFYTaalKp03zKuT80le5Va5nKyp/JpKzPGamNUrmcrDqHzzaSufpSubWVz9j5tZXL27zaa5eizi05rk6hZybyuPqBObXXF26Mmsrj7NGTaVxdw0ZtpXD3Dxm1jj7hvw2lcPcNFG8ri7gfhq4+o78NZXJ1A/LXlydQk0dEcvUSvVcrm6iVqNpXP1yjajXWPURvRpKxsRtCpWViVqrlRYlehs7EporUWJzU9KxK1FanCzmejAmg0YH5L0MH8lqhrQtVD/gtaSGjNPprypGZWt+YeM0635UjJNrr5w8ZM7XZwaMUu7gfJOu3h3ijXfxXeKb07+KFsE69DilnBPp38V1cy13cU9ck67eaeMS12cVWuSb1/x2cVauZenRzV6URrolXpRO63laM6o1o1Z0Ra1jRSiNXrRSidXK00qnV6tSCtOLUhFq4rEJ03zuM35pr3KpGapWVNGa5WZ/NUrLpSuS9c/R65LjGnjJcrm6N5NZXL0Hm1lcvTvNrK5Og82srk6CcWs6cfcCcm3NcXcNGTbmuLuHjNtzXF3BjJvK4u4aM2krj6gxm2lcfcCaOiVxdwJzac1ydQs0bSuXrklqNua5euUrZtZXP1z/AIjajSVz2JXq1lYdc/tC9WsrG8oaUXKzvKNqq1neYlaFSovKdqjWdidqHpZE7UPU4Walowv5P0fkf5/idLBio05IaKFtXh/MvSpD0zL03k1WmabWsilMk3p0cqVyRa6OYpGKL06+YeuKLXbwaMEV28D5J13cB4ovTv4dOCbXocUvin07eK7xTa7+KMYp9u3mnjBPp181WuSbXXzcVrmWunmq0zT6dHNXpmWt5V86J1vGmlE+lxelE60jRSidaRalU6qLVqVq4rWidVD/AJLVPCxk/NJXuVSMjZU0ZNJWZ4yVrHr+nrkuVhf6pXJcrGqVyVrm6/hvFrK5ev46cW0rn7dOLWVydQvkuVx9wJyayuTuBOTaX9OPqDGTeVx9Q0ZNZXF3D+TeVxdwfJvK5O4Hm0lcfXLpzb81xdQk5to5euSzRpK5uuSWzb81y986jfNvK5bMRtResuuUb5tZYw65RvRpKwvKF81ys7EL1XKzvKN6DUeU7VGpsTtCtReSTALyWap9F4DzHo/Lvwn0PJozHoeT1yTaqcqVzL0uRSuReo0kWpkm9NZFa4p9N+YrXFF6dPMUrgj06uYpGCL07OIbwRrs5N4IvTt4H50Xp38BOCbXdwXwR6dvFd4Fa7eKMYp9O3imrin06+ap4j065Va4o9OrmqVxL03lVrkm9a6Of4vTJPtvF60T6axatE+lxWlRrSLVgtXFawVaRSsFpnrI1UeQjB+aPbtPGK9Z01cTlZ0/iuVl0euSpWPR65NJWFPXJo5ej1yVrn6/hpyaTpzdB5NZXL06cW8rl6CcGkrk6JODTmuTuDGTolcXQxi1lcfcN5Nua5Oo6M28rj7gzk1lcnUL5N+a5OuS2yb81y9RO2bWVy9cp2zayubrlK+bWVz9co2zaysLyhpm11jeUL0XrK8IXorWN5RvVUqLyhbNXpPhK1B6R4Tmg9J8EtmXpPgv4F6Hh34T6LyaM0+h5NXMvQ8nrkXtU4Wrkn0ucrVxL00nKtck+2ki1ck+m85WpizvbbnlauCL06uYpXFN6dXPJ450Xp28Q1cE+nZxHeCL07eXeCfTt4dGCPTs4L4p9O7gIwT6dfJ4xL27OTRin06+VK4JvTp4PXEvToitcj9N+VqUR6joitaDdaqxUtXFK1GtYrWpatSIGtIb+HpmiBpvO+L8017I+KkU9cQimriqVlapGKpWHR64NJWRq4tOa5uv6euLSVh0aMVuXqf9dOLSVz9R3k2lcnUDxbcuToPFcrk6Dxbc1y9DGLeX9OTuD4tpf04+4Hi3lcvUdOTbmuTqBOTWVy3kk5N5XN1wnbJtK5uuEpyayue8I2zaysLwjfNpLjC8IXzazpheGe+avTK8I3zVOmd4Qvmr0zvCFsy9JvCVqD0m8Emg9p+ZPwXsvAeafZfMIzT7HzPGRXsfM1c0+x81K5Fej8L0xL2qcLVxT6X4Xpin0ucL0wT7bTlemCb2355Vrgj06OeVq4JvTp55PHOi9urmG8EXt2cwfnR6dnEDwTenZwHgn27OA+dPt2cB86PTr5N4F6dfJq5F6dXIxgXp08qVxHp08/w8ZF6dHKkZl6bxSKD01ikZj00hoyHprD/kelw8VHpcN/B6UP8AD1T8mMH5s9U9cVypN4nrO00YGytNXE2XSniuVjaamK9YdGjJpK56euLaVh0PkqdOXoZwayufos87aVydFnFrK5eoHk0lcvUd5OiVy9QfFrK5euQ8W0rl7mhOTfmuXrkJybyubrlO2TWdOfrlK2bWVz3hK+TadRzXhHTNp6jG8oXyaemV4RvRfpjfxs98znTK8IXzV6ReEL0P2i/jRvQe2fhG2SfZeCTkPZeCTkn2XzDyR7T8xjIvY+ZoyT7HzVpiV7HzVpin2fhozxL2PC1ME+1+F6YJ9rnDRTEvbWcL15y9tZwvXnRem85Urgi9umcqRgn26eeTRgm9OrmDGCb06+Y7wT7dfMCcEe3XzCzzl7dfAeCL06uR+cvbr5GMU+3VzB8R7dXMNGJenRyaMj9N+TRmJ1W/J65l6bQ3mfppDRQemkNFT9NYb+D0qDWp6rDDVMng/O3pD4q1FPXAtRTxiqVlRjBcrG/08YGz6NGKpWHRq4NJWFNGTT0wpvJpK5qM4NZWHUDyaTpydQs4tubrn6LODWVzdQPFrK5euXTi35rmvLvJtK5+oE4tZ05uuSTk356cvXJJybzpj1ylbJpOnPeMStk0nTHrhC2TSdMLwhfJrO2d4Q0yV6ZXhnvkfpneEL5K9o8IXyT6T80bZD2n5pWyTey+ac4o+ifmXyTex83Rin2n5m8UXsfM9cU+y+alcS9j5r0wK9j5r0wT9D+bRTAvavDRngXtXhopgXtfhevOXtrzwtGCfbacq1wT7b88qVwTenROTRgm9ujnkfAvbq45d4I9OrnmB86fTr5hfnT7dPEDxTenVzHeBe3VzHeI9OnkZxL06eXeI9Onk3ifpvy6Mj9N4aMz9NoPkPTSOip61g/g9XBih6sZoXpQfhWmaMX55rutNGB6i00YnKi01cD1nafxXKztNXFWs+qMYq1h1T1xXKy6GuKpWFPGLWVz9D4tJ0woTi1lc3QTgudOfqE8msrn6gTi2nTm6hfJvK5+uQ8msrm65LObedMLCzm1lYXlO2TWdMLylbNrz0x65Svm1nTK8IXyaTpjeEL5q9M/CF8znTO8M181e0X8aNsi9ov40LZF7L5o2yT7T807ZJvY+ZJxRfyF8y+SfoXzd4s/ZfM9cEXsvmeuBex8lqYF7HzXpgn6F8188R7Hzac+cvZz8bRTAe1eGimBe1z8bRXAva5wrXEvbWcLU5y9tZyeMU3pvzyaME+nRzB8E+nROQ8C9OnnkJwL06eeSzgm9OrmBOKfTp5jvAvTo5jp5y9OmQPIvTq5jvFfptB8RrfkPMtbz+u8la1jvM9ax01OdNIH4P0uO/BzpbvwfpUCaH7DXGL4B16aMVai00YDUWnjA5UUYxXqKeMj1naMYrlY03krWV/g1xXrGmjFUrn6N5NZWFDxazpj1AnJpKwsDxayufok4NJ0w6hbZN+enPYWcm3phYW2LSVj1ynObedsLCWzazpleUrZLlZXlG2TSdMrwhfNc6Z+EL5q9M7yhfIek3hC+R+0+ELYle03hG2aPafmjbJN7HzJbJF7T8yeTP6D5hOSPoPmaMUfQfI1cU/QvmrXFHsvmtTBP0HyXpgPon5r0wH0L5tNMR9D+bTTAfQ/mvXEe1/NeuA9rnC1cB7VOFIwHprzwpXBN7bTgYwHptOR8k3pvI7xT6b88wviXp088lnFN6dHPLpwT6dPMCMB6dHMd4D06JAnIenRC+I9No6cBOm8CMla3gTkerhZzHprHRRU6awPwfpRZofpUL+T1ccNN+n4PiNabTRiadN5GnTeJ6Vp4xPUWm8T1ja6uS5Wdp/JWs6MZL1lR8VysKPiuVjXRk0lZdR04tJ0w6gTi0nTCktk11jYS2TWdMLynOTWdMrCTm2lYXkk5NZWdiV81zpleEr5tJ2yvKN6rnSPKF6K9I8o3zV7T4QvmPaPDPfIvZeEbZJvZeErZIvZfNOckXs/mnOKL+QvmHizvZfN0YM7+QfM1cGf0HzUjBP0HzUrzp+ifmvngn6F816YD3S+bTTAfQvm0UwP6D5r0wP2Pm0UwP2rwtXA/avCtcR7X4U8R7XOT+I9NZyMZF6aTkfJPtvOXeJ+m0hYxTem84d4ptb88hOSfTo5jvAem8hZxHp0SBOQ9NpA8R6b8wlslem0gWoPTaE8z9NC2yV7acknM520gTRXtRZoPSoWc1elQJoPan7cZPjz0YyBG8j0tNXEajT+Z6mu8j1nTxkqVlR8j1m6Mlys6MZLlZU3kqVhQ82kqKM5NJWNhZyXKwsLOTfnplYScmkrKwls2m6xvKVsmk6ZWJ2o1nTOxK1Gk6Z3lG9FzpN5RvQ50jwz2zV6T4RvQvSfCF6D2XhG1E+y8I2om9l4TtkzvZ+E7ZM72PAeSL+QeAjFnfyF8x8WV/IPmeuCPoPmeME/QeFqYJ9l4WrgX0L5tFMB9CvDRngftPzXpgfsfNopgr2XhemB+z8LVwV6PwpXIeleFK5H6XOTeR+lzkYyL00nIzkfptOQnJPprOXeKb0155d5C9N+eQ8S9NuYHiXptIE5D06MLbMem0hJzL025ic5q9NsLahzponbNXppA8z9Lic0V6WWanq4X8nqoX8HtMPwNp6/e83zB00ZBNp65BI+QLTeQZ2mrkEWm8jZ2j5nKkYyVrOu816yo+a5WVCaLlZ0YouM7AnNpKysLObSVlYSc2kuMrE7ZLnSMStm1nTOxK1F+md4RvkudM7yhfNftOIXqqdpsRtQ/ZeEL1L0PCNqJ9jwjaifoPCVqIvY8JzmyvY8FnNnex4CMmd7HgfJnex4GuTK9jwpXFPseFK4o9l4q1MS9l4Wpin2m8NFMR7Lw0UyVO03hemSp2PC9MlTsvCtcVej8ReuSp0PB6Zq9H4PGR+lzk3mqdKnI+Z+2k5dGRemk5HyK1fPJvIvTach4p9NZAnMem0hfMvTWQk5j03LOY1pIS2R+m8ifmPTUts1auJzQa0ws5qlqoS1DnS4lNFelhOY9Hpfyc6UWcz0PRRm8FIxQtI3mRaaMwi08ZnpGjM0UZzDOu/AI0ZmzoeZs6P4VKyoWzays6H5XKmunNWsbCWouWIws0ays7E7ZtNZ4naitTUL0X6ReUr0V6Lyheh+ivMZ70P0nyhehex5QvUex5RtCPReEpzRej8pWzZ3seSzRnez8B5M72PDvwyvY8D5s72PBozZ+x4Vrmj2PCtck+yvC1MS9pvC9cB7LytXA/ZeV6YnOy8r0yX7LwtXJfovK1c1e0+Fa5r9q8nrmv0PJozP2ryeMz9KnBoyP0ucu8j1pOa78Fq8g+Z60gfgvTSQJonW3MJNBrSQPMtayJzQvTWQs0HptE7ZHbVltQ9Wl+Fass5n6XCTQapOc1TpUJOavRktQev8BfwNVr0kUeOkYoEmjMFVIzCTVzCaP5Mh/B6zrvwNI00GooWobJ0VOJsdNFysrCTVcqAiq5U2B+Vs7CTRcqE7VXKmxO8L9JxG9VSpxC9T1PlC9T9F4Z9KjR4QvBaflC9S9DyhaGfseU7I9jynaGd7PwT+M+u9PwWasr0Xg1as70fg8VZXoeFK0Rex4Vrmi9jwtTNPpPlbPNPoeYtSh+y8xelD9F4XpRU6LwvXNU6T4VpmvR5VijSdF5PWq9Hk9aH6Hk8UV6E5NELnS5yP8P0rHRU9Pz/g/wAL00nLvwPVaSB+B6aTkJqWtZAmhelyEtUvTSQs0P01hLUHprCzQ9Mk0GxaU5n6XCWoeqJNFTpUJND0yTVWqJNR6BJqeh6P8vNKmioI8UCTxQEaKhNd+EkP5VrOu/haRvyZUJqesqH5PUO/hosJNFypCYVKikmF6jC2hc6LE7QuVGJXPU4jeFanyhpB6MZ7yNGM+g0Yz3lPo/NZ7lej8oWhn6PErIvSpwlMsr0PJWd6Hk0M70PJ4hlafk9as70WL1ojT8q1qnSxelE3pHlelE6fiL0zV6T4WpUaXlWlVam8rUoco8q1q19F4UrVep8nirSU/J4hcpeTRVeq8j/DPyb+Hp+Xf0aqcjEDVyOmBrSR38LVzkPyerkC0JaSFmo1chbQGkhJqFlmh6qJ2qGiVqmoswfoEmitPCTU5VEmh+jlJNBpk/B6Ho4q5Co1qCPEAqatQk8VKproqRD+QihFSSb+K0qH8Gs7CzB6gIVE2BMGgswqUk5XqLCSqUsTucpI3leliN5Ep4z3lWljNpYvQZ9LF6PGbSS9HjPeWfo/KF7ItVkRtLO0sJ/UXpXl39ZXoYaGdo8qVZ2jyrVGli1IR6HlekI9FeV6FpYvSE+i8rUqPQ8tFaq0YrWFSos1WsNJS8q1qrS8qVhfoeTxC5Sw0Q0lGHiFyjyP8V6VjpVKfkT1WBEDTnJogrV460FOlyFGrkdEDWkgTBWqkIJVlk9XIWYGqTtBqkTtAVidoPTJMHqoS0HpkPQX+DQWYHoPRfxngH+EnBgA4SaBiab+FUhBJriQBAVIsLIRYSVJx39VqcLJpsJJlidoPRiV1bE2JXPSxnvI0Yz6WP0eMuli1WM2lhp4zaWTaryy3ujTxC90aeJWlFpzkk3ZXpXkf0z3/pYaJRaPKtbotHnFaSztGL5o0saKM9LF6DS8r5ynU+V6QIXlaqx5XzlUpeVar1PlSIXKXlSrQeTwrRh/40lOQVSl5N/V6eG/pyn5dCtVgzYafkP0NV5d+S1UjpJXko1phSVI6QvCWlWqxO0jVeSSeqJY5TwkmeEsZkkwnYwSQAmQHoArDpwjRALBJGGgFYaLFU2OksTgf0JwCqHf0E7+qKwtoNGEkJpLGWElRYlawGEtYHiF5UWIXGn5ZtJI5GbWQryyaXLT8sut01eMul02jGe+jPTxKZRarC/pnR5d+kBStkUlaXRSxWl2dGL0smjGmkoosaKSRYvnJI8tFJMeF6SZeVqSZeVayvS8ni7SJ8q1suUeTxKzw36VKPJosuUeRiyj8miVafkf6cV5D9Hp4P8ARqsCbBWB/SPy6SPyEqVhLWCpCTIXhQeFsFYSZUeJ2so8TtYywkypWJzYxhP0BhJkDHpIkKdAKw8WCDRJYQxKSwYsED+gmu/qUhMmVhZkYjAmxEH7OFhZsZYSbAsJaT0vKVpUPKd7AeahpcK8s+lweMulweMuhK8suli0/LLpZGqkZNNE1WM97o0eUpui1Xkv6Z0Y6dElhq3RT8q53RSxfOyKWNNLoqcaM9CTjTS6CxelgMaK2BNFJMlayYxWtlwsVrK5Rh4aQYeLLgvJ4uYnJqyqU/I/pejyaJPRgxKtOR36Csd+grHfo9GF/YV5Cbg/Ifozwn9GLkCbGvyT9nIc5Ttc8VidtFYMTtoYxObnBhJsoEmQCzYAv6BvSf08PBrYiH9AYMWCbB/ZYnB/pIsGLEl37CHTYFhZsCwP0EYW1gWEtIIsyrAnawwJ2kwlawPEb2B+WfSwVIza2TqvLJpcleWTWyarGXWUK8sulk1Xlm0ugTlG1yq8J+2YyOi5F5PW6aMitdEFi9dUYjF6apHloz0TS8tWeicT5aM9RhY0U1IsXpoorF63BYtW6jw8WWMUi6yvKno0h4atjHk/6MeRi6h5H9nDx37UrI6dAeO/atEjv2F+a79geS/sKnJZ1OK8knRSsLN1QEtcwna6sVhJueFif6VgwlrmMTm5jCzcDCTdWHhfQYWPSRdLXyMaBOGjQsRg/sid+wQ/sFYMXJFd+xiMD0KJd6DEhOgLCzc8Is3GFhLXMYS1gMTtcGjfQtViF9EnIhe4VIzaWCsZdLpaTlk1uVVjJrZCsZdLJGM15SflG1kH5TixU8d+0pwY1IeVa6opYrTRODGnPQiytOeicTjTndJeWmliwvNaM7DCxelxg8r0uY8rV1MvNVrqoWKVuseTxos/MNF1F5NGgOcm9FRXkfQx5d6LE5dOoHmB6CKkx3orDyh6GeFnQ8PyFtDh4nOyzwltlH5JOysKwltTHlK2qpD8pzqrB5JOp+SwltFTkYWbn5GEnQ5yMLOivIx6X1YY6MGLjEYPoSbBjY8RYaNSTY79liXehYVd6kh06Agm4ThZ0CbA9QWFnQDCW1AwltSGJzok/Kd7hWI30CpGe2gPyzaaJrScxmvcjkZNNEtMZNdCVIy6ylflmvZJ+YhaxUZEplFHmBN0l5GNAPKtbhN5XpomxF5aM9E4WNOeiRjVloReY0Z6Jwry000GFi9NBgxetjGKV0OF5itdVDzFa6nDw8bKLDxdR4b0B+XftUHkfQx5d6KVOQ9TPzRjU4rAnUz8knZZeSzqeHhZ1VIeEnVcgwttVLxK2y5BhJ3X5LCTsqcnhJ1XOSwk7KnJWJ22X5LCzsMGFnY/Iws7DBj0caufG+OjVOJw0blicGNBicd6lYiw3sWIsd7DE2D6liQ9STjvYsLAnYiwJ1BYWdgMJOoGFtqBiVtAvylbQqfmJWulXlC+oX5ZtNU1UjLponF+WbXQlzlk11C/LLpogSM19BVSI2unDSm6cLIE3KweY70LC8nroScXz2IsaKagsac9CxN5aaaJsLGnPQsKxpz0IvK9NAPNXpoB5q1dDweVI0MeVK6Gc4h40PFeBjUx5H0A8m9Dipw71UPIxqo8CdjPyE7qPyX3XIXkk7qkV5LOrSQYWdlYWEnY5Dwnq0nIwltWs5gwk6tJyWEtq0kLCW0V5GEnZU5owk6q8jCTqqcDC+p+EhOheDeh+hw42wfcYTvcsLHewsRhvdOJsH2LEWOjZOIsNHQMRjvcisd7FhYE7FheS+pEE6gF9QeEtqkyTqFJTsDkSvqGmM+miVYz32JcjPfQq0xl00SrGXTUsVIy6bFisZr6lg8o22SPKU6lheQnUsHl0aEnFK3LCxStxhY0U0RifLRTYYPLTTYk4002BY057FgxemxYrytTYYPK1NzweVI2GDyeNjPDxsC8mjYznIzqeK8m9VYfl3seDA9jPyWd14XkJ3VisD3VIeFnoaYMJO6iws7LkGEnoayDyS27WQsJOzScnhJ2aTksLOrSclhJ0XOSJOzScgs7K8gs6q8gPQeQHqflOP2fd5WN8d7jE4aOhOFgx0jE4b3GJsdG6bEYb6SwrNGN02IvLvcsRjvYsLHe5YVgfQWF5L9BYfkJ3LFeSTsSvJLbFisTtsSsRvsFecRvqFyIX3LFzln03Sryy66livLNfUsV5ZtNCVjNfQsPEralgS9BgL6JwY79jCw8aDE+VabJsT5XpsWJ8tFNSTY056lhNGehYMaaaiwYvTRODKvTUDFqakc5PGwV5UruD8mjc8PwPsasH3M8d7qg8hO6ofkvueHhfZcgD3VIMLPS0weQnpVB5LO65B5LPQ2nKST0NpyMLPQ0nKbCzs1kLCTu2kGF9ms5FhZ2XOUYX2aTkYE6K8jC+qvJYHqfkYX1HiFj9KOp4bpx31DE4MdBYjDfQMLHfSEWGjpGJw30pxOD9JeSd9KcIfpRhZB+gYnyE9JYPIT0lg8lt0FYrE56E4eEtunFyEtsMViNugsVIjfcl4jfclyM1+glyM+m5YryzX3GK8s19ixWI31GHkRtqVGRO2pYPJfYYm8ujcYnypXcvJYrTYeUr00T5C9NE3ksaKalifLTnsMLy0U2Kw/LRTZOHi1dk4eKV1GHisblgw0bnivIx0ngwfoGK8h9CsVkdPQeHkLboVIeFnpUWBPQvD8knoXIA+lSfITu05gwk9DaQsd7tZBYWd23lGF9msgyBOzecpwvq1nJYX1azhOO9W04Kws6r8lgeipyMCNF+RgTofksD9q8DFvqfN46Md9IwsGOosRYeOoYgY6isKwY6CxN5NHUlOG+oJwfpLCwfpGFgfQjBjvoThYH0DDkCelOLws9KbDkTnqLFeU7dCcXOcSt0Fi8Sv1JVOWe/QVipEb7pX5Z79AVjPp0A2e+4CFugsPErdAwk53AD3L9AY1PAaNhhYpXYYVi9NisLGjPoTibGnPoThea0U6CwY0Z7lgxopuWKnK1ehOHileglYeNk4MP7qh473PDwJ6RivLvpPD8knqPFYW3SeH5JPQuQYH0rkLyWelYsCehpOU3kPoayFgT0tZCx30NueUh9DbnksdHQ2kTjvobyE6dm3PKMd6tpyQTq1nJWOnVpOSD0X5Aeq5AH7VOSx37PyMZvrfK43o/UMIfqGEP1JxFho6xibDR1DEm+tNhDHUWJox1pxOD9ZYB+sid9ZYeFnpTYrCz1FgwLdJWKws9KFYlbrLF4jPUmxcid+osXIjfqKxeM1+tOGhp1DDxC/SMNC/QeHiNuhGKSt0KkTidugYQT0DCNHQV5LD16Cwla9AwL06BgaKbpLGjPcsNoz3KwNOe6cC9OgjVr1Jw/KlepOH5PHSMVg/SMV5D6jxXkJ6grzCT1nhlnpPBhZ6l4rySeo5B5hfqXCvP+FnqaSFgfU1iXfU2kRgx1NZyVg16W/PKKMdDfnlJo2b88pd7N+eSGNW3PKaaNW05IPRpOU0fRrOSd6NJyB9F+QEXV5IP2fkY/F+t8l5b08dZZU4P1lhD9R4RvrLE0Y7E2IPXsGFho60YRvqGJwfrRgx31kMd9ScUH1psBZ6yPC27CxeJz1psUnPUWNE7daTiVuwsUhfrTikbdIxWIX6RhoX6yw4jfsTikbdYwJz1HOSpJ6VZUh9AwsGvQWDDx0F5JSvQXksWp1DKWNNOpGDGjPqLyMac+svJ40Z9SaMXp0Fi5yrXpGKxWvQmw5B+osXIP0jFSBPQMVhfqGKws9QwYnbsXDwluw1ZST1nCv6LPY1kAfY1k/ScH62nMRlGOptzCwY6G85/4iwY6G8iLD16XRzyiw8dDo55Rh43bc8keurfnlJ41bTlNN6NZyQxdpOSH0aTlLvVfkBOivId+1ef8Dyn1vjLGox1lgGvYMKnr2DEj9ZFpo6yz/pDHUMSb6ysI0dicLDR2JwY77E+Rg/Yi8jCz1lipCz1psVISetOKhbdhYqJW7CWjbsThpW6xiolbrLFxC/WWBG3YMOIX7BikbdgwJW7BgJPUWFS/WeJD6xgNHUMB46iwsVp1CwlqdScDRn1EGjPqRYeNGfSWHI006kYqRavWmxeK16yxUUjqCsN9RKkdPYFSBPYFYnPWFYWeoGSes4aduxeAs9apCL9bWQnfU18pwPpbcwjfS35iapXqb88oqlelvzGVikbujnlnVKdDok/4hWuzfnlKld285Kq11azlJ41bTlJvVpOU0f20nJO9FeQPsryHei/MDwUdT4ny1GOosLRr1DBp/qLCH6wmmjrThGjrKwGjrTiRjrIhjrLAH1pod9ZYcd9ZXmKgT1ovKyT1pNO3anFxK3YVhpT1pwJW6isUlbrLFxG/WMDPfsLFo26xgRt1jyE56hhaWeo8SH1jC0PrLBox1kR46xharXrA1bPrLFa007CwNGfYnDaKdicVItTrRio0V6isUvXqRY0ho6yxcg/UWB30pxpjp6lKJPWnAnPYcipErdi5yeEnrXIRJ62mAJ7Gk5GDHY2nLPBjqdHMTVK9LeRJ46W/PKKvTpdM5Z1anQ355Z1auzpnLOrV2bc8oWrq3nJK12azlKkatZEU3s0nKTRZpOQf9KnMA+q8DvQYHzGOt8Ti9GOseS00dSfKTR1DAMdRYDfUMIY6ywaMdafI031o8k7602Eb6yyqD6iwwnrKw4WetOKJPWnyEr9ibFJT2JxaVuwryErdifKtSv2lho27Cw9Rv1jD1K3YeDUZ7CwanPYMTaWewFpJ7Rha77BhOjsPApXrGBWvYnAtTrLycaM+tCminWSl6dZYtenYmxUac+xF5XFa9pY0h47E+VH+sYqQPrRiyz2DDxO3Wc5VCT1q8qJPWuQEnrVgLPW0nIL9bWQqMdTWcsz063RzEVWvS6OeUrU6XTzyirU6HTOWdaKdDonLOtOe7o55Z1opq1nKF6bN+eUVauraRNUjVpOU6pGrSRJ/RUhaeLL8jTVsrCGLmevkUdL4UaMdIGu+kYNN9RYNGOk8LTfUWFo/WnBpo6SxLvqTitd9IsGu+tOHK77E+TCetF5Mk9abD0lussPUrdacVqV+sWHqVutPlWpW6y8hG/WXk0bdg8nqNuweRqVuxPktSt2DBqduoYNJ9QwtgT0jynXR0jyNNHSPI9Gr1Fg9KV6k4rVqdhWG059qfJxoz604uL59icaNFexOKi+fWVjSK/YnGkUjrLDN9SbFut1Fi4T7Bii26zw4SepWGS3WqQ0561yEH1NJA6ettzykY6nRIg1OlvzyhenS6eYitFOh0yIrRTodPM1nWinQ3kRWvPodE5ZVqpu3kQvTVtOU1eu7acoVpquRC1dFyBSuqsSeNVeSNFznINFlYHxT6HwidH6QNH6QNH6EjXR0jC0fpLyNGOkeRox0p8g31FgdHUVhh9KcGhPUWHKWeksPSz1J8qlTt1JvI1O3UnIrUrdacPUbdRWHqVussHpK3UMHpG3UWD0lfpGD0lbqLB6SnqGQaSeoZBpJ6xhbC/WeJ131jBox1pwaeOsYcqletNitWp1JsEWr1JxcrRl2IsaStFetFjSVop1pxcXz60tIrXrJpFI6hi4f604bvrGNIS3UMUWesYcJPWqQ056jkBZ6WkgD6ms5SEdTokLTx0t+YzUp0ujnlDRn0OnnkmrPd1TlFact3VzGdas9nROWdrVnu3kZ1qz3bTlDRno3nOIrTTVpiF6bKnKatS65ylWl1YSsXVOS/R4ufkHiyvJ6+Eer4DGGj6jBo+wwa6dRg0fYYNH2LBo+5DXe5YNdHQk9D6AcrvpLD0s9JWHpZ6U4CW6E4ZJ6SsGpW6SxUqVulNPUrdRYNRv1Jw9Rt1Fg1K3UMLUrdZeRqVukvJekrdI8l6JPQWFpZ6Bg9F9xg0PcsGj7jBpo6QcqleolavTqTYvV6dKbFLU6WeNJWjPqKxcq9OpFjSVenUixrKtHUWNIevWlopHSFB9AXpfqCtLPSch6Sek5BpJ6VyDQnqaSJ0PpbyFrvobSFqkbujmI1bPd08xLRlu6uYm1qy2dPPKK2ZbOrmIrXnq6ZzrOtWe7o5jPWrPVrzEtWezbENNNFyIrTno0xFWpqc5StS6sKrVurCUjQYnVIsMGv5/9nwDHR9wNd7AaPuBro3LFDO4wO+gYB9xgD3SAncYA90YCTuWHpbbkeknoLD1Od02HqdugsGpW3SNStsBqN904NStuWDUL7jC1K+4wtSvunC1O3QMGknoGDSz0jCLPQWB30kB+gHp46Cw5TxujFynr0JWvToC5WivQzsXFqdKbGsaKdCMXFs+lNjWVevQlpDR0psayqR0hWj9JHAncsUnO65FBO6hpJ3XIC/Q15n7Do6G0iNNG7o5harXZ0c8pXz2dXMDTns6eYzta8tnVzE2teOrq5iLWvLZ1c8s6156tpGdastW0hNeWrWcs61Z6NJymtOeisQ00schNFLKxFWpc01WtgStZAfzx6Pz9zaHqBo+gGh6Aa71CtGNgND1A0I1Ia71LBoewxeunZODSzsmjSTsmwaSdk4adtiCVtgErblgRvuQStunAjbYYWoX3GDUrbJGp21LBqc7DEaSdRg0k6kND1BeneqcHoY1I9GNkqlUruFRWu6LFyqV2Q1aM9wuL12Z1rKtn0E0lWrsitIvXoQ0isbk2g+5Kho3LFO9xhyhOy4ZJ2VIWl9msg0J1aQnezbmEaNnTzAtTV0cxK+erp5ia1ZauvmE15aOrmJrXnq6+Yiteero5jOteWjfmIrXno3kS2ZaNZEteeimdrVndWJrTTQYlopYYlelxiarSwwlYuMD/9k="));

		this.mockMvc.perform(multipart("/image/uploadForUser")
		.file(image))
		
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("La imagen se ha subido correctamente")));
	}

	@Test
	void testAnonymousUploadForItinerary() throws Exception {
		
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "text/plain", "an image".getBytes());

		this.mockMvc.perform(multipart("/image/uploadForItinerary/{itineraryId}", TEST_ITINERARY_ID)
		.file("multipartFile",image.getBytes()))
		
		// Validate the response code and content type
		.andExpect(status().isForbidden())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El usuario no tiene permiso de creación sin registrarse.")));
	}
	
	@Test
	@WithMockUser(username = "user2", password = "user2")
	void testUploadForNotAuthorizedItinerary() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user2");
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "image/jpeg", Base64.getDecoder().decode("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"));

		this.mockMvc.perform(multipart("/image/uploadForItinerary/{itineraryId}", TEST_ITINERARY_ID)
		.file(image))
		
		// Validate the response code and content type
		.andExpect(status().isForbidden())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("No es posible añadir imágenes a un itinerario del que no es dueño.")));
	}
	
	@Test
	@WithMockUser(username = "user2", password = "user2")
	void testUploadForNotExistantItinerary() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user2");
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "image/jpeg", Base64.getDecoder().decode("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"));

		this.mockMvc.perform(multipart("/image/uploadForItinerary/{itineraryId}", TEST_INEXISTENT_ID)
		.file(image))
		
		// Validate the response code and content type
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El itinerario que intenta asociar a la imagen no existe")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testUploadForItinerary() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user1");
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "image/jpeg", Base64.getDecoder().decode("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"));

		this.mockMvc.perform(multipart("/image/uploadForItinerary/{itineraryId}", TEST_ITINERARY_ID)
		.file(image))
		
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("La imagen se ha subido correctamente")));
	}

	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testWrongUploadForItinerary() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user1");
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "text/plain", new byte[]{});

		this.mockMvc.perform(multipart("/image/uploadForItinerary/{itineraryId}", TEST_ITINERARY_ID)
		.file("multipartFile",image.getBytes()))
		
		// Validate the response code and content type
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("La imagen no es válida")));
	}

	@Test
	void testAnonymousUploadForLandmark() throws Exception {
		
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "text/plain", "an image".getBytes());

		this.mockMvc.perform(multipart("/image/uploadForLandmark/{landmarkId}", TEST_LANDMARK_ID)
		.file("multipartFile",image.getBytes()))
		
		// Validate the response code and content type
		.andExpect(status().isForbidden())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El usuario no tiene permiso de creación sin registrarse.")));
	}

	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testWrongUploadForLandmark() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user1");
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "text/plain", new byte[]{});

		this.mockMvc.perform(multipart("/image/uploadForLandmark/{landmarkId}", TEST_LANDMARK_ID)
		.file("multipartFile",image.getBytes()))
		
		// Validate the response code and content type
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("La imagen no es válida")));
	}
	
	@Test
	@WithMockUser(username = "user2", password = "user2")
	void testUploadForNotExistantLandmark() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user2");
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "image/jpeg", Base64.getDecoder().decode("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"));

		this.mockMvc.perform(multipart("/image/uploadForLandmark/{landmarkId}", TEST_INEXISTENT_ID)
		.file(image))
		
		// Validate the response code and content type
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("El POI que intenta asociar a la imagen no existe")));
	}
	
	@Test
	@WithMockUser(username = "user1", password = "user1")
	void testUploadForLandmark() throws Exception {
		given(this.userService.getCurrentUsername()).willReturn("user1");
		MockMultipartFile image = new MockMultipartFile("multipartFile", "image.jpg", "image/jpeg", Base64.getDecoder().decode("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"));

		this.mockMvc.perform(multipart("/image/uploadForLandmark/{landmarkId}", TEST_ITINERARY_ID)
		.file(image))
		
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		

		// Validate the returned fields
		.andExpect(jsonPath("$.text", is("La imagen se ha subido correctamente")));
	}
}