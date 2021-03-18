package com.yourney.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yourney.security.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.username=:username")
	Optional<User> findByUsername(@Param("username") String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
}
