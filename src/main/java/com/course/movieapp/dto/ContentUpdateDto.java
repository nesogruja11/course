package com.course.movieapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContentUpdateDto extends ContentDto {

	private Integer contentId;
}
