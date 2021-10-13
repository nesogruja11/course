package com.course.movieapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.movieapp.dto.GenreDto;
import com.course.movieapp.model.Genre;
import com.course.movieapp.service.GenreService;

import javassist.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

	@Autowired
	GenreService genreService;

	@GetMapping
	public Genre findById(@RequestParam int id) throws NotFoundException {
		return genreService.findById(id);
	}

	@PostMapping("/save")
	public Genre save(@Valid @RequestBody GenreDto genreDto) throws NotFoundException {
		return genreService.save(genreDto);
	}

	@PutMapping("/update")
	public Genre update(@Valid @RequestBody Genre genre) throws NotFoundException {
		return genreService.update(genre);
	}

	@DeleteMapping("/delete")
	public void delete(@RequestParam int id) throws NotFoundException {
		genreService.delete(id);
	}

	@GetMapping("/all")
	public List<Genre> getAll(){
		return genreService.getAll();
	}
}
