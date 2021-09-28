package com.course.movieapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MovieCastKey implements Serializable {
    @Column(name = "content_id", nullable = false)
    private int contentId;

    @Column(name = "movie_role_id", nullable = false)
    private int movieRoleId;

    @Column(name = "movie_people_id", nullable = false)
    private int moviePeopleId;
}
