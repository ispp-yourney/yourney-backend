package com.yourney.security.service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

import com.yourney.security.model.SecureToken;
import com.yourney.security.model.User;
import com.yourney.security.repository.SecureTokenRepository;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

@Service
public class SecureTokenService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private int tokenValidityInSeconds = 4 * 3600;

    @Autowired
    private SecureTokenRepository secureTokenRepository;

    public int getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

    public SecureToken createSecureToken(User user) {
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), UTF_8);

        SecureToken secureToken = new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpiteAt(LocalDateTime.now().plusSeconds(getTokenValidityInSeconds()));
        secureToken.setUser(user);
        this.save(secureToken);

        return secureToken;
    }

    public SecureToken save(SecureToken secureToken) {
        SecureToken newToken = null;

        try {
            newToken = secureTokenRepository.save(secureToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newToken;
    }

    public Optional<SecureToken> findByToken(String token) {
        return secureTokenRepository.findByToken(token);
    }

    public Optional<SecureToken> findByEmail(String email) {
        return secureTokenRepository.findByUserEmail(email);
    }

    public void deleteToken(SecureToken token) {
        secureTokenRepository.delete(token);
    }

    public void deleteTokenByToken(String token) {
        secureTokenRepository.deleteByToken(token);
    }

}
