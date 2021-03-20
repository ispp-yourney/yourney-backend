package com.yourney.security.repository;

import java.util.Optional;

import com.yourney.security.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT user FROM User user WHERE user.username=:username")
	Optional<User> findByUsername(@Param("username") String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
}
