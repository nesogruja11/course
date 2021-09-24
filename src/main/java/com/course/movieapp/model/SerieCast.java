package com.course.movieapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "serie_cast")
public class SerieCast {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serie_cast_id", nullable = false)
	private int serieCastId;

	@ManyToOne
	@JoinColumn(name = "season_id", nullable = false)
	private Season season;

	@ManyToOne
	@JoinColumn(name = "movie_people_id", nullable = false)
	private MoviePeople moviePeople;

	@ManyToOne
	@JoinColumn(name = "movie_role_id", nullable = false)
	private MovieRole movieRole;
}
