package com.course.movieapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.ContentGenre;
import com.course.movieapp.model.Genre;
import com.course.movieapp.repository.ContentGenreRepository;

import javassist.NotFoundException;

@Service
public class ContentGenreService {

	@Autowired
	ContentGenreRepository contentGenreRepository;

	@Autowired
	ContentService contentService;

	@Autowired
	GenreService genreService;

	public List<ContentGenre> findAllByContent(Content content) throws NotFoundException {
		if (contentService.existById(content.getContentId())) {
			return contentGenreRepository.findAllByContent(content);
		}
		throw new NotFoundException("Nije pronađen sadržaj sa id-em:" + content.getContentId());
	}

	public List<ContentGenre> findAllByGenreId(int genreId) throws NotFoundException {
		Genre genre = genreService.findById(genreId);
		return contentGenreRepository.findAllByGenre(genre);
	}

}
