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
public class SaveSerieDto extends ContentDto {

	private List<Season> seasons;
}
