package com.yourney.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourney.security.entity.Role;
import com.yourney.security.enums.RoleType;
import com.yourney.security.repository.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	public Optional<Role> getByRoleType(RoleType roleType) {
		return roleRepository.findByRoleType(roleType);
	}
}
