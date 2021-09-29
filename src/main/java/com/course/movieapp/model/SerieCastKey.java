package com.course.movieapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SerieCastKey implements Serializable {

	@Column(name = "season_id", nullable = false)
	private int seasonId;

	@Column(name = "movie_role_id", nullable = false)
	private int movieRoleId;

	@Column(name = "movie_people_id", nullable = false)
	private int moviePeopleId;
}
