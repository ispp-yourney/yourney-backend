package com.yourney.model;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.model.dto.Message;
class MessageDtoModelTests {

	Message m1;
	
	@BeforeEach
	void setup() {
		m1 = new Message();
		m1.setText("Bienvenido a Yourney");
	}

	@Test
	void testEqualsClass() {
		EqualsVerifier.simple().forClass(Message.class).verify();
	}

	@Test
	void testHashcode() {
		assertNotNull(m1.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals("",m1.toString());
	}
}