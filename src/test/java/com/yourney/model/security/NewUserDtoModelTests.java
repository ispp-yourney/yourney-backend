package com.yourney.model.security;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.security.model.dto.NewUser;
class NewUserDtoModelTests {

	NewUser m1;
	
	@BeforeEach
	void setup() {
		m1 = new NewUser();
		m1.setEmail("user@mail.com");
		m1.setRoles(new HashSet<String>());
	}

	@Test
	public void testEqualsClass() {
		EqualsVerifier.simple().forClass(NewUser.class).verify();
	}

	@Test
	void testHashcode() {
		assertNotNull(m1.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals(m1.toString(),"");
	}
}