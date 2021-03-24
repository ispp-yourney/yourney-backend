package com.yourney.security.jwt;

import java.util.Date;

import com.yourney.security.model.PrincipalUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private int expiration;

	public String generateToken(Authentication authentication) {
		PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
		return Jwts.builder().setSubject(principalUser.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean valitateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Malformed token");
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupperted token");
		} catch (ExpiredJwtException e) {
			logger.error("Expired token");
		} catch (IllegalArgumentException e) {
			logger.error("Empty token");
		} catch (SignatureException e) {
			logger.error("Signature error");
		}

		return false;
	}
}
