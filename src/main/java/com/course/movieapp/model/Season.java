package com.course.movieapp.model;

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
@Table(name = "season")
public class Season {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seasonId;

	@ManyToOne
	@JoinColumn(name = "content_id", nullable = false)
	private Content content;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "season_number", nullable = false)
	private int seasonNumber;

	@OneToMany(mappedBy = "season")
	private List<SerieCast> serieCastList;

	@OneToMany(mappedBy = "season")
	private List<Episode> episodes;
}
