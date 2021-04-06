package com.yourney.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yourney.security.model.User;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @NotBlank
	@Column(nullable = false)
	@Length(max = 1000)
	private String content;

    @Column(nullable = false)
	@Min(1)
    @Max(5)
    private Integer rating;

    @Column(name = "create_date", nullable = false)
	private LocalDateTime createDate;

    @ManyToOne
	private User author;

    @ManyToOne
    @JsonBackReference
	private Itinerary itinerary;

    public Long getItineraryId() {
        return itinerary.getId();
    }
    
}
