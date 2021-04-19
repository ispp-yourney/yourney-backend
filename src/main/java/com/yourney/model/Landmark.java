package com.yourney.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Entity
@Data
@Table(name = "landmarks", indexes = { @Index(columnList = "country, city, name") })
public class Landmark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	@Length(max = 50)
	private String name;

	@NotBlank
	@Column(nullable = false)
	@Length(max = 1000)
	private String description;

	@Range(min = 0, max = 10000)
	private Double price;

	@NotBlank
	@Column(nullable = false)
	@Length(max = 100)
	private String country;

	@NotBlank
	@Column(nullable = false)
	@Length(max = 100)
	private String city;

	@Range(min = -90, max = 90)
	private Double latitude;

	@Range(min = -180, max = 180)
	private Double longitude;

	private LocalDateTime endPromotionDate;

	@Length(max = 50)
	private String category;

	// Información de contacto

	@Email
	@Length(max = 100)
	private String email;

	@Length(max = 50)
	@Pattern(regexp = "^(([+][(][0-9]{1,3}[)][ ])?([0-9]{6,12}))$")
	private String phone;

	@URL
	@Length(max = 300)
	private String website;

	@URL
	@Length(max = 300)
	private String instagram;

	@URL
	@Length(max = 300)
	private String twitter;

	private LocalDateTime createDate;

	private Long views;

	@OneToOne(cascade = CascadeType.DETACH)
	private Image image;

	@Formula("(SELECT image.image_url FROM images image WHERE image.id=image_id)")
	private String imageUrl;
}
