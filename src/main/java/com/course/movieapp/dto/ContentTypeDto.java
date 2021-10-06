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
public class ContentTypeDto {
	@NotNull(message = "Naziv tipa sadržaja je obavezan!")
	private String name;
}
