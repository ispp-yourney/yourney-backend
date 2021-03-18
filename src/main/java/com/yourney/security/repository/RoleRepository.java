package com.yourney.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yourney.security.entity.Role;
import com.yourney.security.enums.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleType(RoleType roleType);
	
}
