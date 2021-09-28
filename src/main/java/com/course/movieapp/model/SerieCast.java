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
@Table(name = "serie_cast")
public class SerieCast {

	@EmbeddedId
	@Column(name = "serie_cast_id", nullable = false)
	private SerieCastKey serieCastKey;

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
