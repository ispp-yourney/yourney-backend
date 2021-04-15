
package com.yourney.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

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
import org.springframework.security.test.context.support.WithMockUser;

import com.yourney.model.Activity;
import com.yourney.model.Itinerary;
import com.yourney.model.Landmark;
import com.yourney.model.StatusType;
import com.yourney.repository.ActivityRepository;
import com.yourney.repository.ItineraryRepository;
import com.yourney.security.model.User;
import com.yourney.security.repository.UserRepository;
import com.yourney.security.service.UserService;


@SpringBootTest
class UserServiceTests {

	private static final String	TEST_USER_USERNAME	= "user1";
	private static final String	TEST_USER_EMAIL	= "testuser@email.com";
	
	@Autowired
	protected UserService userService;
	
	@MockBean
	private UserRepository userRepository;

	public User auth1 = new User();
	public Activity a2 = new Activity(); 
	public Activity a3 = new Activity(); 
	public Activity a4 = new Activity(); 
	
	@BeforeEach
	void setup() {
		
		//User
		auth1.setId((long)1);
		auth1.setEmail(TEST_USER_EMAIL);
		auth1.setFirstName("Name 1");
		auth1.setLastName("Surname 1");
		auth1.setPassword("user1");
		auth1.setUsername(TEST_USER_USERNAME);
		auth1.setPlan(0);
		
		given(this.userRepository.findByUsername(auth1.getUsername())).willReturn(Optional.of(auth1));
		given(this.userRepository.existsByUsername(auth1.getUsername())).willReturn(true);
		given(this.userRepository.existsByEmail(auth1.getEmail())).willReturn(true);
		doReturn(auth1).when(this.userRepository).save(any());
//		given(this.userService.getCurrentUsername()).willReturn(auth1.getUsername());
	
	}
	
	@Test
	void testGetByUsername() {

		Optional<User> expected = this.userService.getByUsername(TEST_USER_USERNAME);

		assertTrue(expected.isPresent());
		assertSame(expected.get(), this.auth1);
		
		
	}
	
	@Test
	void testexistByUsername() {

		Boolean expected = this.userService.existsByUsername(TEST_USER_USERNAME);

		assertTrue(expected);
		
	}
	
	@Test
	void testexistByEmail() {

		Boolean expected = this.userService.existsByEmail(TEST_USER_EMAIL);

		assertTrue(expected);
		
	}
	
	@Test
	@WithMockUser(username = TEST_USER_USERNAME, password = "user1")
	void testGetCurrentUsername() {

		String expected = this.userService.getCurrentUsername(); 
		System.out.println(expected);
		System.out.println(TEST_USER_USERNAME);
		assertEquals(expected, this.auth1.getUsername());
	}
	
}
