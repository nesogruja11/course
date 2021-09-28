package com.course.movieapp.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content")
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contentId;

	@ManyToOne
	@JoinColumn(name = "content_type_id", nullable = false)
	private ContentType contentType;

	@ManyToOne
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;

	@ManyToOne
	@JoinColumn(name = "language_id", nullable = false)
	private Language language;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "year", nullable = false)
	private int year;

	@Column(name = "duration", nullable = false)
	private int duration;

	@Column(name = "release_date", nullable = false)
	private LocalDate releaseDate;

	@Column(name = "rating", nullable = false)
	private float rating = 0;

	@Column(name = "cover_link", nullable = false)
	private String coverLink;

	@Column(name = "trailer_link", nullable = false)
	private String trailerLink;

	@OneToMany(mappedBy = "content")
	private List<ContentGenre> contentGenreList;

	@Column(name = "active", nullable = false, columnDefinition = "boolean default true")
	private boolean active = true;
}
