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

import com.course.movieapp.dto.MovieRoleDto;
import com.course.movieapp.model.MovieRole;
import com.course.movieapp.service.MovieRoleService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/movie-role")
public class MovieRoleController {

	@Autowired
	MovieRoleService movieRoleService;

	@GetMapping
	private MovieRole findById(@RequestParam int id) throws NotFoundException {
		return movieRoleService.findById(id);
	}

	@PostMapping("/save")
	private MovieRole save(@Valid @RequestBody MovieRoleDto movieRoleDto) {
		return movieRoleService.save(movieRoleDto);
	}

	@PutMapping("/update")
	private MovieRole update(@Valid @RequestBody MovieRole movieRole) throws NotFoundException {
		return movieRoleService.update(movieRole);
	}

	@DeleteMapping("/delete")
	private void delete(@RequestParam int id) throws NotFoundException {
		movieRoleService.delete(id);
	}
}
