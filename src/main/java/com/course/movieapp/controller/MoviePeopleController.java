package com.course.movieapp.controller;

import com.course.movieapp.dto.MoviePeopleDto;
import com.course.movieapp.dto.MoviePeopleUpdateDto;
import com.course.movieapp.model.MoviePeople;
import com.course.movieapp.service.MoviePeopleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie-people")
public class MoviePeopleController {

    @Autowired
    MoviePeopleService moviePeopleService;

    @PostMapping("/save")
    public MoviePeople save(@RequestBody MoviePeopleDto moviePeopleDto){
        return moviePeopleService.save(moviePeopleDto);
    }

    @GetMapping
    public MoviePeople findById(@RequestParam int id) throws NotFoundException {
        return moviePeopleService.findById(id);
    }

    @PutMapping("/update")
    public MoviePeople update(@RequestBody MoviePeopleUpdateDto moviePeopleUpdateDto) throws NotFoundException {
        return moviePeopleService.update(moviePeopleUpdateDto);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody MoviePeople moviePeople) throws NotFoundException {
        moviePeopleService.delete(moviePeople);
    }
}
