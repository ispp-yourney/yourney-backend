package com.yourney.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
	@Length(max=50)
	private String name;
	
	@Length(max=255)
	private String description;
	
	@Range(min=0)
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
	
	@Length(max=50)
	private String phone;
	
	@URL
	private String website;
	
	@Length(max=50)
	private String instagram;
	
	@URL
	private String twitter;
	
}
