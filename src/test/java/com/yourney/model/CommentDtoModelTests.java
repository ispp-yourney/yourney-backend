package com.yourney.model;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.model.dto.CommentDto;
class CommentDtoModelTests {

	CommentDto c1;
	Comment c2;
	CommentDto c3;
	
	@BeforeEach
	void setup() {
		c1 = new CommentDto();
		c1.setContent("Content1");
		c1.setItinerary(null);
		c1.setRating(5);

		c2 = new Comment();
		c2.setAuthor(null);
		c2.setContent("content2");
		c2.setCreateDate(LocalDateTime.now());
		c2.setId(2);
		c2.setItinerary(null);
		c2.setRating(5);

		c3 = new CommentDto(1l, "content3", 5);

	}

	@Test
	void testEqualsClass() {
		EqualsVerifier.simple().forClass(CommentDto.class).verify();
	}

	@Test
	void testHashcode() {
		assertNotNull(c1.hashCode());
		assertNotNull(c2.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals("",c1.toString());
		assertNotEquals("",c2.toString());
	}
}