package com.course.movieapp.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie_cast")
public class MovieCast {

	@EmbeddedId
	@Column(name = "movie_cast_id", nullable = false)
	private MovieCastKey movieCastKey;

	@MapsId("contentId")
	@ManyToOne
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
