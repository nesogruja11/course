package com.course.movieapp.dto;

import com.course.movieapp.model.ContentType;
import com.course.movieapp.model.Country;
import com.course.movieapp.model.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ContentDto {

    private int contentTypeId;
    private int coutryId;
    private int languageId;
    private String title;
    private int year;
    private int duration;
    private LocalDate releaseDate;
    private String coverLink;
    private String trailerLink;
    private List<Integer> genreIds;
}
