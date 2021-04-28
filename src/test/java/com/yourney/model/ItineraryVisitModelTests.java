package com.yourney.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import javax.persistence.Id;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class ItineraryVisitModelTests {

	ItineraryVisit a1;
	ItineraryVisit a2;
	Itinerary it1;
	Itinerary it2;

	@BeforeEach
	void setup() {

		// ITINERARIOS
		
	    it1 = new Itinerary();
	    it1.setId((long)1);
	    it1.setName("itinerary test 1");
	    it1.setDescription("lorem ipsum 1");
	    it1.setStatus(StatusType.PUBLISHED);
	    it1.setBudget(10.);
	    it1.setEstimatedDays(2);
	    it1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it1.setViews(0);
	    it1.setAuthor(null);
	    
	    it2 = new Itinerary();
	    it2.setId((long)2);
	    it2.setName("itinerary test 2");
	    it2.setDescription("lorem ipsum 2");
	    it2.setStatus(StatusType.PUBLISHED);
	    it2.setBudget(100.);
	    it2.setEstimatedDays(3);
	    it2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
	    it2.setViews(50);
	    it2.setAuthor(null);
	    
	    
		a1 = new ItineraryVisit(); 
		a1.setId((long) 1);
		a1.setIp("127.0.0.1");
		a1.setItinerary(it1);

		a2 = new ItineraryVisit(); 
		a2.setId((long) 2);
		a2.setIp("127.0.0.2");
		a2.setItinerary(it2);

	}

	@Test
	void testEqualsClass() {
		EqualsVerifier.simple().withPrefabValues(Itinerary.class, it1, it2).forClass(ItineraryVisit.class).withIgnoredAnnotations(Id.class).verify();
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