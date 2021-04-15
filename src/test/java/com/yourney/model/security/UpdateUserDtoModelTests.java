package com.yourney.model.security;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import com.yourney.model.Image;
import com.yourney.security.model.PrincipalUser;
import com.yourney.security.model.Role;
import com.yourney.security.model.RoleType;
import com.yourney.security.model.User;
import com.yourney.security.model.dto.UpdateUser;
class UpdateUserDtoModelTests {

	UpdateUser m1;
	PrincipalUser pu1;
	User us1;
	Role ro1;
	
	@BeforeEach
	void setup() {
		m1 = new UpdateUser();
		m1.setEmail("user@mail.com");
		m1.setFirstName("firstName");
		m1.setLastName("lastName");

		// ROLES
		ro1 = new Role(1l, RoleType.ROLE_USER);
		Set<Role> roles = new HashSet<>();
		roles.add(ro1);

		us1 = new User();
		us1.setId((long)1);
		us1.setEmail("testuser@email.com");
		us1.setFirstName("Name 1");
		us1.setLastName("Surname 1");
		us1.setPassword("user1");
		us1.setUsername("user1");
		us1.setPlan(0);
		us1.setRoles(roles);
		us1.setExpirationDate(LocalDateTime.now());
		us1.setImage(new Image());
		us1.setImageUrl("https://github.com/");
		us1.setPlan(0);

		pu1 = new PrincipalUser("pu1", "pu1", "pu1@pu1.com", "pu1", "pu1", List.of());
		pu1.setAuthorities(List.of());
		pu1.setEmail("pu1@pu1.com");
		pu1.setFirstName("pu1");
		pu1.setLastName("pu1");
		pu1.setPassword("pu1");
		pu1.setUsername("pu1");

		PrincipalUser.build(us1);
	}

	@Test
	public void testEqualsClass() {
		EqualsVerifier.simple().forClass(UpdateUser.class).verify();
	}

	@Test
	void testHashcode() {
		assertNotNull(m1.hashCode());
	}

	@Test
	void testToString() {
		assertNotEquals(ro1.toString(),"");
		assertNotEquals(pu1.toString(),"");
		assertNotEquals(m1.toString(),"");
	}
}