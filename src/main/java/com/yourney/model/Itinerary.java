package com.yourney.model;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yourney.security.model.User;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "itineraries")
public class Itinerary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Column(nullable = false)
	@Length(max = 50)
	private String name;

	@NotBlank
	@Column(nullable = false)
	@Length(max = 1000)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusType status;

	private Double budget;

	@Column(name = "estimated_days")
	private Integer estimatedDays;

	@Column(name = "create_date", nullable = false)
	private LocalDateTime createDate;

	private Integer views;

	@OneToOne(cascade = CascadeType.DETACH)
	private Image image;

	@Formula("(SELECT image.image_url FROM images image WHERE image.id=image_id)")
	private String imageUrl;

	@Enumerated(EnumType.STRING)
	private SeasonType recommendedSeason;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "itinerary")
	private Collection<Activity> activities;

	@ManyToOne
	private User author;

	public String getUsername() {
		return this.author.getUsername();
	}

	@Formula("(select case when u.expiration_date >= CURRENT_DATE then u.plan else 0 end from users u where u.id=author_id)")
	private Integer calcPlan;

	@Formula("(select count(ac.id) from activities ac left join landmarks land on ac.landmark_id=land.id where ac.itinerary_id=id and land.end_promotion_date >= CURRENT_DATE)")
	private long calcPromotion;

	@JsonManagedReference
	@OrderBy("createDate desc")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "itinerary")
	private Collection<Comment> comments;

	@Formula("(select avg(c.rating) from comments c where c.itinerary_id = id)")
	private Double avgRating;

	@Formula("(select count(c.id) from comments c where c.itinerary_id = id)")
	private Long countComments;

}
