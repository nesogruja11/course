package com.course.movieapp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentRatingDto {

	private int userId;
	private int contentId;
	@Min(1)
	@Max(5)
	private int rating;
}
