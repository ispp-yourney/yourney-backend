package com.yourney.model.activities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.yourney.model.itineraries.Itinerary;

import lombok.Data;

@Entity
@Data
@Table(name = "activities")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	@Length(max=50)
	private String name;
	
	@Length(max=255)
	private String description;
	
	@Positive
	@Column(nullable = false)
	private Integer day;
	
	@Range(min=0)
	private Integer view;
	
	@OneToOne
	private Landmark landmark;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Itinerary itinerary;
	
	@OneToMany
	private Collection<Image> images;
}
