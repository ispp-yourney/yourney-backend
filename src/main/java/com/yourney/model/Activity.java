package com.yourney.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "activities")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Length(max = 50)
	private String title;

	private String description;

	@Positive
	@Column(nullable = false)
	private Integer day;

	private LocalDateTime deleteDate;

	private LocalDateTime updateDate;

	private LocalDateTime createDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusType status;

	private Integer views;

	@JsonBackReference
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Itinerary itinerary;

	@ManyToOne(optional = true)
	private Landmark landmark;

}