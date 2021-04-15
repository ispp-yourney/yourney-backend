package com.yourney.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.model.dto.ItineraryDto;

class ItineraryDtoModelTests {

	ItineraryDto l1;
	ItineraryDto l2;

	@BeforeEach
	void setup() {

		//Itinerary
	    l1 = new ItineraryDto((long)1, "Giralda", "lorem ipsum", 0., null, null, null, null);
	    
	    l2 = new ItineraryDto();
	    l2.setName("Coliseo romano");
	    l2.setDescription("lorem ipsum");
	    l2.setBudget(0.);
		l2.setEstimatedDays(5);
		l2.setRecommendedSeason(SeasonType.SUMMER);
		l2.setId(2);
		l2.setStatus(StatusType.PUBLISHED);
		l2.setCreateDate(LocalDateTime.now());
	}
	
	@Test
	public void testEqualsClass() {
		EqualsVerifier.simple().forClass(ItineraryDto.class).verify();
	}

	@Test
	void testIsNotEqual() {
		assertNotEquals(l1, l2);
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