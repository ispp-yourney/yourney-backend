package com.yourney.model.users;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "users", indexes = { 
		@Index(columnList = "username"), 
		@Index(columnList = "email"),
		@Index(columnList = "creation_date") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(unique = true)
	@Length(max = 15)
	private String username;

	@NotBlank
	@Length(max = 15)
	private String name;

	@NotBlank
	@Length(max = 30)
	@Column(name = "last_name")
	private String lastName;

	@Column(unique = true, nullable = false)
	@Length(max = 30)
	@Email
	private String email;

	@NotBlank
	@Length(max = 50)
	private String password;

	@NotBlank
	@Length(min = 2, max = 2)
	private String country;

	@NotBlank
	@Length(max = 15)
	private String sector;

	@PastOrPresent
	@Column(name = "creation_date", nullable = false)
	@DateTimeFormat(pattern = "HH:mm:ss dd/MM/yyyy 'GMT'")
	private Date creationDate;

	@PastOrPresent
	@Column(name = "delete_date")
	@DateTimeFormat(pattern = "HH:mm:ss dd/MM/yyyy 'GMT'")
	private Date deleteDate;

	@PastOrPresent
	@Column(name = "last_connection", nullable = false)
	@DateTimeFormat(pattern = "HH:mm:ss dd/MM/yyyy 'GMT'")
	private Date lastConnection;

	@Column(nullable = false)
	private Boolean active;
}
