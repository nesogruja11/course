package com.course.movieapp.dto;

import java.time.LocalDate;
import java.util.List;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.ContentComment;
import com.course.movieapp.model.ContentType;
import com.course.movieapp.model.Country;
import com.course.movieapp.model.Genre;
import com.course.movieapp.model.Language;
import com.course.movieapp.model.MovieCast;
import com.course.movieapp.model.Season;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentDetailsDto {

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
	private List<ContentComment> contentComments;
	private List<Season> seasons;

	public ContentDetailsDto(Content content, List<Genre> genres, List<MovieCast> movieCastList,
			List<ContentComment> contentComments, List<Season> seasons) {
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
		this.contentComments = contentComments;
		this.seasons = seasons;
	}
}
