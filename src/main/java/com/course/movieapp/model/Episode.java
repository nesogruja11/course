package com.course.movieapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "episode")
public class Episode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int episodeId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "season_id", nullable = false)
	private Season season;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "duration", nullable = false)
	private int duration;

	@Column(name = "episodeNumber", nullable = false)
	private int episodeNumber;
}
