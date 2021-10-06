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
@Table(name = "movie_cast")
public class MovieCast {

	@EmbeddedId
	@JsonIgnore
	@Column(name = "movie_cast_id", nullable = false)
	private MovieCastKey movieCastKey;

	@MapsId("contentId")
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "content_id", nullable = false)
	private Content content;

	@MapsId("moviePeopleId")
	@ManyToOne
	@JoinColumn(name = "movie_people_id", nullable = false)
	private MoviePeople moviePeople;

	@MapsId("movieRoleId")
	@ManyToOne
	@JoinColumn(name = "movie_role_id", nullable = false)
	private MovieRole movieRole;
}
