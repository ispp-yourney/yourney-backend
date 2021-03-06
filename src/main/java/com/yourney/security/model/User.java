package com.yourney.security.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yourney.model.Image;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean enabled;

	@Column(unique = true, nullable = false)
	@Length(max = 50, message = "No se permiten más de 50 caracteres")
	private String username;

	@JsonIgnore
	@Length(max = 50, message = "No se permiten más de 50 caracteres")
	@Column(nullable = false)
	private String password;

	private String email;

	@Column(name = "first_name")
	@Length(max = 50, message = "No se permiten más de 50 caracteres")
	private String firstName;

	@Length(max = 50, message = "No se permiten más de 50 caracteres")
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "expiration_date")
	private LocalDateTime expirationDate;

	// 0=Free
	// 1=Basic
	@Column(nullable = false)
	private Integer plan;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@OneToOne(cascade = CascadeType.DETACH)
	private Image image;

	@Formula("(SELECT image.image_url FROM images image WHERE image.id=image_id)")
	private String imageUrl;

	public User(String username, String password, String email, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;

		this.enabled = false;
	}

}
