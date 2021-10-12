package com.course.movieapp.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
@Table(name = "serie_cast")
public class SerieCast {

	@JsonIgnore
	@EmbeddedId
	@Column(name = "serie_cast_id", nullable = false)
	private SerieCastKey serieCastKey;

	@JsonIgnore
	@MapsId("seasonId")
	@ManyToOne
	@JoinColumn(name = "season_id", nullable = false)
	private Season season;

	@MapsId("moviePeopleId")
	@ManyToOne
	@JoinColumn(name = "movie_people_id", nullable = false)
	private MoviePeople moviePeople;

	@MapsId("movieRoleId")
	@ManyToOne
	@JoinColumn(name = "movie_role_id", nullable = false)
	private MovieRole movieRole;

}
