package com.yourney.security.repository;

import java.util.Optional;

import com.yourney.security.model.SecureToken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecureTokenRepository extends CrudRepository<SecureToken, Long> {

    Optional<SecureToken> findByToken(String token);

    void deleteByToken(String token);

}
