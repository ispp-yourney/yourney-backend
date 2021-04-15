package com.yourney.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.model.dto.AuthorDto;
class AuthorDtoModelTests {

	AuthorDto a1;
	AuthorDto a2;
	
	@BeforeEach
	void setup() {
		a1 = new AuthorDto();
		a1.setEmail("author1@email.com");
		a1.setFirstName("Author 1");
		a1.setId(1L);
		a1.setLastName("lastName 1");
		a1.setUsername("username1");

		a2 = new AuthorDto();
		a2.setEmail("author2@email.com");
		a2.setFirstName("Author 2");
		a2.setId(2L);
		a2.setLastName("lastName 2");
		a2.setUsername("username2");
	
	}

	@Test
	public void testEqualsClass() {
		EqualsVerifier.simple().forClass(AuthorDto.class).verify();
	}

	@Test
	void testIsNotEqual() {
		assertNotEquals(a2, a1);
	}

	@Test
	void testIsEqual() {
		assertEquals(a1, a1);
	}
	
	@Test
	void testHashcode() {
		assertNotNull(a1.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals(a1.toString(),"");
	}
}