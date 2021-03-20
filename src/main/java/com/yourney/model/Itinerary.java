package com.yourney.model;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.yourney.security.model.User;

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
	private String	name;

	@NotBlank
	@Column(nullable = false)
	@Length(max = 255)
	private String	description;

	// TODO Solo mostrar públicos
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusType	status;

	private Double	budget;

	// TODO Calculado al publicar un itinerario
	@Column(name = "estimated_days")
	private Integer	estimatedDays;

	@Column(name = "create_date", nullable = false)
	private LocalDateTime	createDate;

	@Column(name = "update_date")
	private LocalDateTime	updateDate;

	@Column(name = "delete_date")
	private LocalDateTime	deleteDate;

	private Integer	views;

	@ManyToMany
	@JoinTable(
			name = "itineraries_recommended_seasons",
			joinColumns = @JoinColumn(name = "itinerary_id"), 
			inverseJoinColumns = @JoinColumn(name = "season_id"))
	private Collection<Season> recommendedSeasons;
	

	@ManyToOne()
	private User author;
}
