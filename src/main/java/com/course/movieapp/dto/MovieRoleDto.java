package com.course.movieapp.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieRoleDto {

	@NotNull(message = "Naziv uloge je obavezan!")
	private String name;
}
