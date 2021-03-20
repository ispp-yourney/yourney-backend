package com.yourney.security.repository;

import java.util.Optional;

import com.yourney.security.model.Role;
import com.yourney.security.model.RoleType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleType(RoleType roleType);
	
}
