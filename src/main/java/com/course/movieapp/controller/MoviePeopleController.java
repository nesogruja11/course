package com.course.movieapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.movieapp.dto.MoviePeopleDto;
import com.course.movieapp.dto.MoviePeopleUpdateDto;
import com.course.movieapp.model.MoviePeople;
import com.course.movieapp.service.MoviePeopleService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/movie-people")
public class MoviePeopleController {

	@Autowired
	MoviePeopleService moviePeopleService;

	@PostMapping("/save")
	public MoviePeople save(@RequestBody MoviePeopleDto moviePeopleDto) {
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
	public void delete(@RequestParam int id) throws NotFoundException {
		moviePeopleService.delete(id);
	}
}
