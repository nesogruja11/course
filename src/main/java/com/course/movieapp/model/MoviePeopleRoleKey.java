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
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MoviePeopleRoleKey implements Serializable {

    @Column(name = "movie_people_id", nullable = false)
    private int moviePeopleId;

    @Column(name = "movie_role_id", nullable = false)
    private int movieRoleId;
}
