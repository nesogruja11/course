package com.course.movieapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeasonDto {

    private Integer seasonId;
    private int contentId;
    private String name;
    private int seasonNumber;
}
