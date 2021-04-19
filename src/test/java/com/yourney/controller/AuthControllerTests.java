package com.yourney.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.yourney.model.Image;
import com.yourney.security.controller.AuthController;
import com.yourney.security.model.Role;
import com.yourney.security.model.RoleType;
import com.yourney.security.model.User;
import com.yourney.security.service.RoleService;
import com.yourney.security.service.UserService;
import com.yourney.service.ImageService;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTests {

	private static final int TEST_USER_ID_1 = 1;
	private static final int TEST_USER_NOT_FOUND = 4;
	private static final int TEST_USER_ID_2 = 2;
	private static final String TEST_USERNAME = "user1";
	private static final String TEST_USERNAME_NOT_FOUND = "unregistered";
	private static final int TEST_DEFAULT_IMAGE_ID = 78;
	

	
	@Autowired
	protected AuthController authController;

	@MockBean
	private UserService userService;
	
	@MockBean
	private RoleService roleService;
	
	@Autowired
	protected ImageController imageController;
	
	@MockBean
	protected ImageService imageService;
	
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		
		// ROLES
		
		Role ro1 = new Role();
		
		ro1.setId((long)TEST_USER_ID_1);
		ro1.setRoleType(RoleType.ROLE_USER);
		
		Set<Role> roles = new HashSet<>();
		roles.add(ro1);
		
		//IMAGES
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
		
		us2.setEmail("user2@email.com");
		us2.setExpirationDate(null);
		us2.setFirstName("Firstname2");
		us2.setId((long)TEST_USER_ID_2);
		us2.setLastName("Lastname2");
		us2.setPassword("password2");
		us2.setPlan(0);
		us2.setRoles(roles);
		us2.setUsername("user2");
		
		
		
	    given(this.userService.getCurrentUsername()).willReturn(us1.getUsername());
	    given(this.userService.getByUsername(us1.getUsername())).willReturn(Optional.of(us1));
	    given(this.roleService.getByRoleType(RoleType.ROLE_USER)).willReturn(Optional.of(ro1));
	    given(this.imageService.findById((long)TEST_DEFAULT_IMAGE_ID)).willReturn(Optional.of(defaultImage));
	    doReturn(us1).when(this.userService).save(any());
	    
	    
	    
	}
	
	@Test
	void testShowUser() throws Exception {
		this.mockMvc.perform(get("/auth/show/{username}", TEST_USERNAME))
		// Validate the response code and content type
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.firstName", is("Name 1")))
        .andExpect(jsonPath("$.lastName", is("Surname 1")))
        .andExpect(jsonPath("$.email", is("testuser@email.com")));
	}
	
	@Test
	void testShowUserNotFound() throws Exception {
		this.mockMvc.perform(get("/auth/show/{username}", TEST_USERNAME_NOT_FOUND))
		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("No existe el usuario indicado")));
	}
	
	@Test
	void testNewUser() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("email", "testuser@email.com");
		activityJSON.put("firstName", "Name 1");
		activityJSON.put("lastName", "Surname 1");
		activityJSON.put("password", "user1");
		activityJSON.put("username", "user1");
		
		this.mockMvc.perform(post("/auth/new")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isCreated())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("Usuario creado correctamente")));
	}
	
	@Test
	void testNewUserUsernameAlreadyExist() throws Exception {
		
		given(this.userService.existsByUsername(any())).willReturn(true);
	    
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("email", "testuser@email.com");
		activityJSON.put("firstName", "Name 1");
		activityJSON.put("lastName", "Surname 1");
		activityJSON.put("password", "user1");
		activityJSON.put("username", "user1");
		
		this.mockMvc.perform(post("/auth/new")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("Nombre de usuario ya existente")));
	}
	
	@Test
	void testNewUserEmailAlreadyExist() throws Exception {
		
		given(this.userService.existsByEmail(any())).willReturn(true);
		 
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("email", "testuser@email.com");
		activityJSON.put("firstName", "Name 1");
		activityJSON.put("lastName", "Surname 1");
		activityJSON.put("password", "user1");
		activityJSON.put("username", "user1");
		
		this.mockMvc.perform(post("/auth/new")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("Email ya existente")));
	}
	
	@Test
	void testNewUserNoUserRole() throws Exception {
		
		given(this.roleService.getByRoleType(RoleType.ROLE_USER)).willReturn(Optional.empty());
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("email", "testuser@email.com");
		activityJSON.put("firstName", "Name 1");
		activityJSON.put("lastName", "Surname 1");
		activityJSON.put("password", "user1");
		activityJSON.put("username", "user1");
		
		this.mockMvc.perform(post("/auth/new")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("El rol de usuario no se encuentra disponible.")));
	}
	
	@Test
	void testUpdateUser() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("email", "testuserupdated@email.com");
		activityJSON.put("firstName", "Name updated");
		activityJSON.put("lastName", "Surname updated");
		activityJSON.put("id", TEST_USER_ID_1);
		
		this.mockMvc.perform(put("/auth/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isOk())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("Usuario actualizado correctamente")));
	}
	
	@Test
	void testUpdateUserNotFound() throws Exception {
		
		given(this.userService.getByUsername(any())).willReturn(Optional.empty());
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("email", "testuserupdated@email.com");
		activityJSON.put("firstName", "Name updated");
		activityJSON.put("lastName", "Surname updated");
		activityJSON.put("id", TEST_USER_NOT_FOUND);
		
		this.mockMvc.perform(put("/auth/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("No existe el usuario indicado")));
	}
	
	@Test
	void testUpdateUserEmailAlreadyExist() throws Exception {
		
		given(this.userService.existsByEmail(any())).willReturn(true);
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("email", "testuserupdated@email.com");
		activityJSON.put("firstName", "Name updated");
		activityJSON.put("lastName", "Surname updated");
		activityJSON.put("id", TEST_USER_NOT_FOUND);
		
		this.mockMvc.perform(put("/auth/update")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().is4xxClientError())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("Email ya existente")));
	}
	
	@Test
	void testUpgradeUser() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("subscriptionDays", 33);
		
		this.mockMvc.perform(get("/auth/upgrade")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isOk())
        

//		// Validate the returned fields
        .andExpect(jsonPath("$.id", is(TEST_USER_ID_1)))
		.andExpect(jsonPath("$.firstName", is("Name 1")))
		.andExpect(jsonPath("$.lastName", is("Surname 1")));
	}
	
	@Test
	void testFailedLogin() throws Exception {
		
		JSONObject activityJSON = new JSONObject();
		
		activityJSON.put("password", "user3");
		activityJSON.put("username", "user3");
		
		this.mockMvc.perform(post("/auth/login")
		.contentType(MediaType.APPLICATION_JSON)
		.content(activityJSON.toString()))

		// Validate the response code and content type
		.andExpect(status().isUnauthorized())
        

		// Validate the returned fields
        .andExpect(jsonPath("$.text", is("El usuario o la contraseña es inválido")));
	}
}