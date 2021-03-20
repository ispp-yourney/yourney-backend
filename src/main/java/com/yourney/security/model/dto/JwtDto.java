package com.yourney.security.model.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class JwtDto {
	
	private String token;
	private String bearer = "Bearer";
	
	private String username;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public JwtDto(String token, String username, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.token = token;
		this.username = username;
		this.authorities = authorities;
	}
	
	
}
