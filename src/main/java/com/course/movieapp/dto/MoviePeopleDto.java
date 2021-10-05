package com.course.movieapp.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoviePeopleDto {

	private String firstName;
	private String lastName;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	private char gender;
	List<Integer> moviePeopleRoleIds;

}
