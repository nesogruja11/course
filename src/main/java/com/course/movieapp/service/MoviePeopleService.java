package com.course.movieapp.service;

import com.course.movieapp.dto.MoviePeopleDto;
import com.course.movieapp.dto.MoviePeopleUpdateDto;
import com.course.movieapp.dto.UserDto;
import com.course.movieapp.model.*;
import com.course.movieapp.repository.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoviePeopleService {

    @Autowired
    MoviePeopleRepository moviePeopleRepository;

    @Autowired
    MovieRoleRepository movieRoleRepository;

    @Autowired
    MoviePeopleRoleRepository moviePeopleRoleRepository;

    // ulazni argument validirati na endpoint-u sa hibernate anotacijom
    // password mora biti hashovan kada se doda security
    public MoviePeople save(MoviePeopleDto moviePeopleDto) {
        MoviePeople moviePeople = moviePeopleRepository.save(moviePeopleDto.buildMoviePeopleFromDto());
        moviePeopleDto.getMoviePeopleRoleIds().forEach(roleId -> {
            MoviePeopleRoleKey key = new MoviePeopleRoleKey(moviePeople.getMoviePeopleId(), roleId);
            MovieRole role = movieRoleRepository.getById(roleId);
            moviePeopleRoleRepository.save(new MoviePeopleRole(key, role, moviePeople));
        });
        return moviePeopleRepository.save(moviePeople);
    }

    public MoviePeople findById(int id) throws NotFoundException {
        return moviePeopleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nije pronađen akter sa id-em:" + id));
    }

    public MoviePeople update(MoviePeopleUpdateDto moviePeopleUpdateDto) throws NotFoundException {
        if (moviePeopleRepository.existsById(moviePeopleUpdateDto.getMoviePeopleId())) {
            moviePeopleRoleRepository.deleteByMoviePeople(findById(moviePeopleUpdateDto.getMoviePeopleId()));
            MoviePeople moviePeople = moviePeopleRepository.save(moviePeopleUpdateDto.buildMoviePeopleFromDto());
            moviePeopleUpdateDto.getMoviePeopleRoleIds().forEach(roleId -> {
                MoviePeopleRoleKey key = new MoviePeopleRoleKey(moviePeopleUpdateDto.getMoviePeopleId(), roleId);
                MovieRole role = movieRoleRepository.getById(roleId);
                moviePeopleRoleRepository.save(new MoviePeopleRole(key, role, moviePeople));
            });

            return moviePeople;
        }
        throw new NotFoundException("Nije pronađen akter sa id-em:" + moviePeopleUpdateDto.getMoviePeopleId());
    }

    public void delete(MoviePeople moviePeople) throws NotFoundException {
        if(moviePeopleRepository.existsById(moviePeople.getMoviePeopleId())){
            moviePeople.setActive(false);
            moviePeopleRepository.save(moviePeople);
        }else{
            throw new NotFoundException("Nije pronađen akter sa id-em:" + moviePeople.getMoviePeopleId());
        }
    }
}
