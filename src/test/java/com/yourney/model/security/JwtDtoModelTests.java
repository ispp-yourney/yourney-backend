package com.yourney.model.security;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.security.model.dto.JwtDto;
class JwtDtoModelTests {

	JwtDto c1;
	
	@BeforeEach
	void setup() {
		c1 = new JwtDto();
		c1.setAuthorities(new ArrayList<GrantedAuthority>());
		c1.setBearer("bearer");
		c1.setToken("token");
		c1.setUsername("username");

	}

	@Test
	public void testEqualsClass() {
		EqualsVerifier.simple().forClass(JwtDto.class).verify();
	}

	@Test
	void testHashcode() {
		assertNotNull(c1.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals(c1.toString(),"");
	}
}