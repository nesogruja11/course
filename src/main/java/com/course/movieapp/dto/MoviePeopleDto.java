package com.course.movieapp.dto;

import com.course.movieapp.model.MoviePeople;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoviePeopleDto {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private char gender;
    List<Integer> moviePeopleRoleIds;

    public MoviePeople buildMoviePeopleFromDto(){
        MoviePeople moviePeople = new MoviePeople();
        moviePeople.setBirthDate(this.getBirthDate());
        moviePeople.setFirstName(this.getFirstName());
        moviePeople.setGender(this.getGender());
        moviePeople.setLastName(this.getLastName());

        return moviePeople;
    }
}
