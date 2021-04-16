package com.yourney.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.model.dto.LandmarkDto;

class LandmarkDtoModelTests {

	LandmarkDto l1;
	LandmarkDto l2;

	@BeforeEach
	void setup() {

		//Landmark
	    l1 = new LandmarkDto((long)1, "Giralda", "lorem ipsum 12", 12., "España", "Sevilla", 37.38618100597202, -5.992615925346369, "Monumento histórico", "giralda@email.com", "123456789", "https://www.twitch.tv/antsuabon", "https://www.instagram.com/antsuabon", "https://twitter.com/antsuabon", LocalDateTime.now().minusDays(5), (long)0, null);
	    
	    l2 = new LandmarkDto();
		l2.setId((long) 2);
	    l2.setName("Coliseo romano");
	    l2.setDescription("lorem ipsum 23");
	    l2.setPrice(0.);
	    l2.setCountry("Italia");
	    l2.setCity("Roma");
	    l2.setLatitude(37.381324231202);
	    l2.setLongitude(-5.99123412343369);
	    l2.setEmail("coliseo@email.com");
	    l2.setInstagram("https://www.instagram.com/juanogtir");
	    l2.setPhone("123416789");
	    l2.setTwitter("https://twitter.com/juanogtir");
	    l2.setWebsite("https://www.twitch.tv/juanogtir");
	    l2.setCategory("Monumento histórico.");
	    l2.setViews((long)0);

	}
	
	@Test
	void testIsNotEqual() {
		assertNotEquals(l1, l2);
	}

	@Test
	public void testEqualsClass() {
		EqualsVerifier.simple().forClass(LandmarkDto.class).verify();
	}

	@Test
	void testIsEqual() {
		assertEquals(l1, l1);
	}
	
	@Test
	void testHashcode() {
		assertNotNull(l1.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals(l1.toString(),"");
	}
}