package com.yourney.model;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.Id;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.model.dto.ImageDto;
class ImageDtoModelTests {

	ImageDto s1;
	Image i1;
	ImageDto s2;
	
	@BeforeEach
	void setup() {
		s1 = new ImageDto();
		s1.setImageUrl("http://itt-sport.com/wp-content/uploads/2011/11/JAVIER-VAZQUEZ.jpg");
		s1.setName("javvazzam");

		i1 = new Image("Javier Vázquez", "http://itt-sport.com/wp-content/uploads/2011/11/JAVIER-VAZQUEZ.jpg", "cloudinaryId1");
		s2 = new ImageDto("Javier Vázquez", "http://itt-sport.com/wp-content/uploads/2011/11/JAVIER-VAZQUEZ.jpg");
	}

	@Test
	void testEqualsClass() {
		EqualsVerifier.simple().forClass(ImageDto.class).verify();
	}

	@Test
	void testEqualsImageClass() {
		EqualsVerifier.simple().forClass(Image.class).withIgnoredAnnotations(Id.class).verify();
		i1.toString();
		i1.hashCode();
	}	

	@Test
	void testHashcode() {
		assertNotNull(s1.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals("",s1.toString());
	}
}