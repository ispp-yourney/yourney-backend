package com.yourney.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import com.yourney.model.dto.ItineraryDto;

class ItineraryDtoModelTests {

	ItineraryDto l1;
	ItineraryDto l2;

	Itinerary it1;
	Itinerary it2;
	Activity ac1;
	Activity ac2;
	Comment c1;
	Comment c2;

	@BeforeEach
	void setup() {

		c1 = new Comment();
		c1.setContent("content1");
		c1.setCreateDate(LocalDateTime.now());
		c1.setId(1);
		c1.setItinerary(it1);
		c1.setRating(5);

		// ITINERARIOS	
		it1 = new Itinerary();
		it1.setId(1L);
		it1.setName("itinerary test 1");
		it1.setDescription("lorem ipsum 1");
		it1.setStatus(StatusType.PUBLISHED);
		it1.setBudget(10.);
		it1.setEstimatedDays(2);
		it1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		it1.setViews(0);
		it1.setActivities(new ArrayList<>());
		it1.setImageUrl("http://itt-sport.com/wp-content/uploads/2011/11/JAVIER-VAZQUEZ.jpg");
		it1.setCalcPromotion(0L);
		it1.setCalcPlan(0);
		it1.setComments(List.of(c1));
		it1.setAvgRating(5.);
		it1.setCountComments(1l);

		it2 = new Itinerary();
		it2.setId((long)2);
		it2.setName("itinerary test 2");
		it2.setDescription("lorem ipsum 2");
		it2.setStatus(StatusType.PUBLISHED);
		it2.setBudget(100.);
		it2.setEstimatedDays(3);
		it2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		it2.setViews(50);

		ac1 = new Activity(); 
		ac1.setId(1);
		ac1.setTitle("activity test 1");
		ac1.setDescription("lorem ipsum 1");
		ac1.setDay(1);
		ac1.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		ac1.setItinerary(it1);

		ac2 = new Activity(); 
		ac2.setId(2);
		ac2.setTitle("activity test 2");
		ac2.setDescription("lorem ipsum 2");
		ac2.setDay(2);
		ac2.setCreateDate(LocalDateTime.of(2021, 01, 20, 12, 25, 01));
		ac2.setItinerary(it2);

		c2 = new Comment();
		c2.setContent("content2");
		c2.setCreateDate(LocalDateTime.now());
		c2.setId(2);
		c2.setItinerary(it2);
		c2.setRating(5);

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
	public void testEqualsLandmarkClass() {
		EqualsVerifier.simple().withPrefabValues(Itinerary.class, it1, it2).forClass(Landmark.class).withIgnoredAnnotations(Id.class).verify();
	}

	@Test
	public void testEqualsCommentClass() {
		EqualsVerifier.simple().withPrefabValues(Itinerary.class, it1, it2).forClass(Comment.class).withIgnoredAnnotations(Id.class).verify();
	}	

	@Test
	public void testEqualsItineraryClass() {
		EqualsVerifier.simple().withPrefabValues(Activity.class, ac1, ac2).withPrefabValues(Comment.class, c1, c2).forClass(Itinerary.class).withIgnoredAnnotations(Id.class).verify();
	}	

	@Test
	public void testEqualsActivityClass() {
		EqualsVerifier.simple().withPrefabValues(Activity.class, ac1, ac2).withPrefabValues(Itinerary.class, it1, it2).forClass(Activity.class).withIgnoredAnnotations(Id.class).verify();
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