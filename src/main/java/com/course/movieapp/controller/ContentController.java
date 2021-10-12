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
import com.course.movieapp.dto.ContentDetailsDto;
import com.course.movieapp.dto.SaveMovieDto;
import com.course.movieapp.dto.SaveSerieDto;
import com.course.movieapp.dto.UpdateMovieDto;
import com.course.movieapp.dto.UpdateSerieDto;
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
	public Content saveMovie(@RequestBody SaveMovieDto saveMovieDto) throws NotFoundException {
		return contentService.saveMovie(saveMovieDto);
	}

	@PutMapping("/update-movie")
	public Content updateMovie(@RequestBody UpdateMovieDto updateMovieDto) throws NotFoundException {
		return contentService.updateMovie(updateMovieDto);
	}

	@DeleteMapping("/delete")
	public void deleteContent(@RequestParam int id) throws NotFoundException {
		contentService.deleteContent(id);
	}

	@PostMapping("/by-category")
	public List<Content> getContentByCategory(@RequestBody ContentByCategoryDto contentByCategoryDto)
			throws NotFoundException {
		return contentService.getContentByCategory(contentByCategoryDto);
	}

	@GetMapping("/details")
	public ContentDetailsDto getContentDetails(@RequestParam int contentId) throws NotFoundException {
		return contentService.getContentDetails(contentId);
	}

	@PostMapping("/save-serie")
	public Content saveSerie(@RequestBody SaveSerieDto saveSerieDto) throws NotFoundException {
		return contentService.saveSerie(saveSerieDto);
	}

	@PutMapping("/update-serie")
	public Content updateSerie(@RequestBody UpdateSerieDto updateSerieDto) throws NotFoundException {
		return contentService.updateSerie(updateSerieDto);
	}

}
