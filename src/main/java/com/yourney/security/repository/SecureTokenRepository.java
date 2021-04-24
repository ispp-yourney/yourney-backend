package com.yourney.security.repository;

import java.util.Optional;

import com.yourney.security.model.SecureToken;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecureTokenRepository extends CrudRepository<SecureToken, Long> {

    Optional<SecureToken> findByToken(String token);

    @Query("select s from SecureToken s where s.user.email = :email")
    Optional<SecureToken> findByUserEmail(@Param("username") String email);

    void deleteByToken(String token);

}
