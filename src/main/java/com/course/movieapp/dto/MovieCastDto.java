package com.course.movieapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieCastDto {

	private int contentId;
	private int movieRoleId;
	private int moviePeopleId;

}
