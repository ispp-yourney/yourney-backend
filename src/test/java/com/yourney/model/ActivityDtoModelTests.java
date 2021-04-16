package com.yourney.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.model.dto.ActivityDto;
class ActivityDtoModelTests {

	ActivityDto a1;
	ActivityDto a2;
	Landmark l1;
	Landmark l2;
	Itinerary it1;
	Itinerary it2;
	ActivityDto a3;


	@BeforeEach
	void setup() {
  
		a1 = new ActivityDto(); 
		a1.setId((long) 1);
		a1.setTitle("comienza el test: Giralda");
		a1.setDescription("lorem ipsum 0");
		a1.setDay(1);

		a2 = new ActivityDto(); 
		a2.setId((long) 2);
		a2.setTitle("comienza el test: Giralda");
		a2.setDescription("lorem ipsum 1");
		a2.setDay(1);

		a3 = new ActivityDto(3l, "title", "description", 1, 1l, 1l);

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
	    
	}

	@Test
	public void testEqualsClass() {
		EqualsVerifier.simple().forClass(ActivityDto.class).verify();
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
		assertNotNull(a3.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals(a1.toString(),"");
	}
}