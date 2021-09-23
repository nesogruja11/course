package com.course.movieapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie_people_role")
public class MoviePeopleRole {

    @EmbeddedId
    @Column(name = "movie_people_role_id", nullable = false)
    private MoviePeopleRoleKey moviePeopleRoleKey;

    @ManyToOne
    @MapsId("movieRoleId")
    @JoinColumn(name = "movie_role_id", nullable = false)
    private MovieRole movieRole;

    @ManyToOne
    @MapsId("moviePeopleId")
    @JoinColumn(name = "movie_people_id", nullable = false)
    private MoviePeople moviePeople;
}
