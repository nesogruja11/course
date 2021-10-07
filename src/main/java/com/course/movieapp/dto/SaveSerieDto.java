package com.course.movieapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveSerieDto extends ContentDto {

	private List<SaveSeasonDto> saveSeasonList;
}
