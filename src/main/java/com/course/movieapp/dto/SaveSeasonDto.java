package com.course.movieapp.dto;

import java.util.List;

import com.course.movieapp.model.Season;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveSeasonDto {

	private String name;
	private int seasonNumber;
	private List<ContentCastDto> serieCastList;
	private List<EpisodeDto> episodes;

	public Season createSeasonFromDto() {
		Season season = new Season();
		season.setName(this.getName());
		season.setSeasonNumber(this.getSeasonNumber());

		return season;
	}

}
