package com.yourney.model.security;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.security.model.dto.LoginUser;
class LoginUserDtoModelTests {

	LoginUser m1;
	
	@BeforeEach
	void setup() {
		m1 = new LoginUser();
		m1.setPassword("password");
		m1.setUsername("username");
	}

	@Test
	void testEqualsClass() {
		EqualsVerifier.simple().forClass(LoginUser.class).verify();
	}

	@Test
	void testHashcode() {
		assertNotNull(m1.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals("",m1.toString());
	}
}