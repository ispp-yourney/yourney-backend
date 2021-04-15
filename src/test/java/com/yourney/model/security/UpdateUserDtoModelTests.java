package com.yourney.model.security;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.security.model.dto.UpdateUser;
class UpdateUserDtoModelTests {

	UpdateUser m1;
	
	@BeforeEach
	void setup() {
		m1 = new UpdateUser();
		m1.setEmail("user@mail.com");
		m1.setFirstName("firstName");
		m1.setLastName("lastName");
	}

	@Test
	public void testEqualsClass() {
		EqualsVerifier.simple().forClass(UpdateUser.class).verify();
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