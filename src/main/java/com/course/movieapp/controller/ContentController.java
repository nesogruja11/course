package com.course.movieapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.movieapp.dto.ContentByCategoryDto;
import com.course.movieapp.dto.ContentDto;
import com.course.movieapp.dto.ContentUpdateDto;
import com.course.movieapp.dto.MovieDetailsDto;
import com.course.movieapp.model.Content;
import com.course.movieapp.service.ContentService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/content")
public class ContentController {

	@Autowired
	ContentService contentService;

	@GetMapping
	public Content findById(@RequestParam int id) throws NotFoundException {
		return contentService.findById(id);
	}

	@PostMapping("/save-movie")
	public Content saveMovie(@RequestBody ContentDto contenDto) throws NotFoundException {
		return contentService.saveMovie(contenDto);
	}

	@PutMapping("/update-movie")
	public Content updateMovie(@RequestBody ContentUpdateDto contentUpdateDto) throws NotFoundException {
		return contentService.updateMovie(contentUpdateDto);
	}

	@DeleteMapping("/delete-movie")
	public void deleteMovie(@RequestParam int id) throws NotFoundException {
		contentService.deleteMovie(id);
	}

	@PostMapping("/by-category")
	public List<Content> getContentByCategory(@RequestBody ContentByCategoryDto contentByCategoryDto)
			throws NotFoundException {
		return contentService.getContentByCategory(contentByCategoryDto);
	}

	@GetMapping("/movie-details")
	public MovieDetailsDto getMovieDetails(@RequestParam int contentId) throws NotFoundException {
		return contentService.getMovieDetails(contentId);
	}

	@PostMapping("/save-serie")
	public Content saveSerie() {
		return null;
	}
}
