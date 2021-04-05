package com.yourney.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.URL;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@URL
	@NotBlank
	@Column(nullable = false)
	private String imageUrl;

	@JsonIgnore
	private String cloudinaryId;

	public Image(String name, String imageUrl, String cloudinaryId) {
		this.name = name;
		this.imageUrl = imageUrl;
		this.cloudinaryId = cloudinaryId;
	}

}
