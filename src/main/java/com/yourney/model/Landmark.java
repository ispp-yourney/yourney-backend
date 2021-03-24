package com.yourney.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Entity
@Data
@Table(name = "landmarks")
public class Landmark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	@Length(max = 50)
	private String name;

	@Length(max = 255)
	private String description;

	@Range(min = 0)
	private Double price;

	private String country;

	private String city;

	private Double latitude;

	private Double longitude;

	private boolean promoted;

	private String category;

	// Información de contacto

	@Email
	private String email;

	@Length(max = 50)
	private String phone;

	@URL
	private String website;

	@Length(max = 50)
	private String instagram;

	@URL
	private String twitter;

	private LocalDateTime deleteDate;

	private LocalDateTime updateDate;

	private LocalDateTime createDate;

	private Long views;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusType status;
}
