package com.course.movieapp.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContentDto {

	private int contentId;
	private int contentTypeId;
	private int coutryId;
	private int languageId;
	private String title;
	private int year;
	private int duration;
	private LocalDate releaseDate;
	private String coverLink;
	private String trailerLink;
	private List<Integer> genreIds;
}
