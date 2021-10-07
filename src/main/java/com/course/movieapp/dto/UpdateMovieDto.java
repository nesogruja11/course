package com.course.movieapp.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMovieDto extends ContentDto {

	private Integer contentId;
	private List<ContentCastDto> movieCastList;
}
