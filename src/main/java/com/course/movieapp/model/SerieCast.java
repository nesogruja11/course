package com.course.movieapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "serie_cast")
public class SerieCast implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    @ManyToOne
    @JoinColumn(name = "movie_people_id", nullable = false)
    private MoviePeople moviePeople;

    @ManyToOne
    @JoinColumn(name = "movie_role_id", nullable = false)
    private MovieRole movieRole;
}
