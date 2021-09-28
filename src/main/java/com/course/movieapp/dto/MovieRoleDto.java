package com.course.movieapp.dto;

import com.course.movieapp.model.MovieRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieRoleDto {
    private String name;

    public MovieRole buildMovieRoleFromDto(){
        MovieRole movieRole = new MovieRole();
        movieRole.setName(this.getName());

        return movieRole;
    }
}
