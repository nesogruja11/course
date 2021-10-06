package com.course.movieapp.dto;

import java.time.LocalDate;
import java.util.List;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.ContentType;
import com.course.movieapp.model.Country;
import com.course.movieapp.model.Genre;
import com.course.movieapp.model.Language;
import com.course.movieapp.model.MovieCast;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDetailsDto {

	private ContentType contentType;
	private Country country;
	private Language language;
	private String title;
	private int year;
	private int duration;
	private LocalDate releaseDate;
	private String coverLink;
	private String trailerLink;
	private List<Genre> genres;
	private List<MovieCast> movieCastList;

	public MovieDetailsDto(Content content, List<Genre> genres, List<MovieCast> movieCastList) {
		this.contentType = content.getContentType();
		this.country = content.getCountry();
		this.language = content.getLanguage();
		this.title = content.getTitle();
		this.year = content.getYear();
		this.duration = content.getDuration();
		this.releaseDate = content.getReleaseDate();
		this.coverLink = content.getCoverLink();
		this.trailerLink = content.getTrailerLink();
		this.genres = genres;
		this.movieCastList = movieCastList;
	}
}
