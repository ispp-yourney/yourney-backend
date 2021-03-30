
package com.yourney.controller.e2e;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.yourney.service.ActivityService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ActivityControllerTestsE2E {

	private static final int	TEST_ACTIVITY_ID	= 1;

	@Autowired
	private MockMvc				mockMvc;
	
	@Autowired
    private ActivityService activityService;


	
	@Test
	void testShowActivityList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/activity/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("activity"));
	}

//	@Test
//	void testShowActivityByItinerary() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/listByItinerary")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("activity"));
//	}
//	
//	@Test
//	void testShowActivity() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/activity/show/{id}", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("activity"));
//	}
//
//	@Test
//	void testProcessCreationFormSuccess() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.post("/activity/create").with(SecurityMockMvcRequestPostProcessors.csrf())
//
//			.param("id", "1").param("title", "titulo").param("description", "descripcion").param("day", "2021-03-04").param("createDate", "2021-03-04"))
//			.andExpect(MockMvcResultMatchers.status().isCreated());
//		;
//	}
//
////	@WithMockUser(username = "admin", authorities = {
////			"admin"
////	})
////	@Test
////	void testProcessCreationFormHasErrors() throws Exception {
////		this.mockMvc.perform(MockMvcRequestBuilders.post("/airports/new").with(SecurityMockMvcRequestPostProcessors.csrf())
////
////			.param("name", "Sevilla Airport").param("maxNumberOfPlanes", "200").param("maxNumberOfClients", "description").param("latitude", "190.123").param("longitude", "78.987").param("code", "VGA").param("city", "Sevilla"))
////			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("airport")).andExpect(MockMvcResultMatchers.view().name("airports/createAirportForm"));
////	}
//
//	@Test
//	void testProcessUpdateFormSuccess() throws Exception {
//		this.mockMvc
//			.perform(MockMvcRequestBuilders.put("/activity/update", activityService.findById(ActivityControllerTestsE2E.TEST_ACTIVITY_ID)).with(SecurityMockMvcRequestPostProcessors.csrf())
//					.param("id", "1").param("title", "titulo").param("description", "descripcion").param("day", "2021-03-04").param("createDate", "2021-03-04"))
//			.andExpect(MockMvcResultMatchers.status().isCreated());
//	}
//
////	@WithMockUser(username = "admin", authorities = {
////			"admin"
////	})
////	@Test
////	void testProcessUpdateFormHasErrors() throws Exception {
////		this.mockMvc
////			.perform(MockMvcRequestBuilders.post("/airports/{airportId}/edit", ActivityControllerTestsE2E.TEST_ACTIVITY_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Betis Airport").param("code", "DEP").param("latitude", "190.345"))
////			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("airport")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("airports/createAirportForm"));
////	}

}
