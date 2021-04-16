package com.yourney.model;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.model.dto.Search;
class SearchDtoModelTests {

	Search s1;
	
	@BeforeEach
	void setup() {
		s1 = new Search();
		s1.setCadena("Bienvenido a Yourney");
		s1.setUserId(1L);
	}

	@Test
	public void testEqualsClass() {
		EqualsVerifier.simple().forClass(Search.class).verify();
	}

	@Test
	void testHashcode() {
		assertNotNull(s1.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals(s1.toString(),"");
	}
}