package com.course.movieapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EpisodeDto {
	private String name;
	private int duration;
	private int episodeNumber;
}
