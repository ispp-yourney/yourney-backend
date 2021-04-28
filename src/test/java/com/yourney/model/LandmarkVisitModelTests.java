package com.yourney.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import javax.persistence.Id;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class LandmarkVisitModelTests {

	LandmarkVisit a1;
	LandmarkVisit a2;
	Landmark l1;
	Landmark l2;

	@BeforeEach
	void setup() {

		//Landmark
	    l1 = new Landmark();
	    l1.setId((long)1);
	    l1.setName("Giralda");
	    l1.setDescription("lorem ipsum");
	    l1.setPrice(0.);
	    l1.setCountry("España");
	    l1.setCity("Sevilla");
	    l1.setLatitude(37.38618100597202);
	    l1.setLongitude(-5.992615925346369);
	    l1.setEndPromotionDate(LocalDateTime.of(2025, 12, 10, 8, 6, 4));
	    l1.setEmail("giralda@email.com");
	    l1.setInstagram(null);
	    l1.setPhone("123456789");
	    l1.setTwitter(null);
	    l1.setWebsite(null);
	    l1.setCategory("Monumento histórico");
	    l1.setViews((long)0);
	    l1.setImage(null);
	    
	    l2 = new Landmark();
	    l2.setId((long) 2);
	    l2.setName("Coliseo romano");
	    l2.setDescription("lorem ipsum");
	    l2.setPrice(0.);
	    l2.setCountry("Italia");
	    l2.setCity("Roma");
	    l2.setLatitude(37.38618100597202);
	    l2.setLongitude(-5.992615925346369);
	    l2.setEndPromotionDate(LocalDateTime.of(2025, 12, 10, 8, 6, 4));
	    l2.setEmail("coliseo@email.com");
	    l2.setInstagram(null);
	    l2.setPhone("123456789");
	    l2.setTwitter(null);
	    l2.setWebsite(null);
	    l2.setCategory("Monumento histórico");
	    l2.setViews((long)0);
	    l2.setImage(null);
	    
		a1 = new LandmarkVisit(); 
		a1.setId((long) 1);
		a1.setIp("127.0.0.1");
		a1.setLandmark(l1);

		a2 = new LandmarkVisit(); 
		a2.setId((long) 2);
		a2.setIp("127.0.0.2");
		a2.setLandmark(l2);

	}

	@Test
	void testEqualsClass() {
		EqualsVerifier.simple().withPrefabValues(Landmark.class, l1, l2).forClass(LandmarkVisit.class).withIgnoredAnnotations(Id.class).verify();
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
		assertNotNull(a2.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals("",a1.toString());
	}
}