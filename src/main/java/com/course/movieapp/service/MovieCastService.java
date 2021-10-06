package com.course.movieapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.MovieCast;
import com.course.movieapp.repository.MovieCastRepository;

import javassist.NotFoundException;

@Service
public class MovieCastService {

	@Autowired
	MovieCastRepository movieCastRepository;

	@Autowired
	ContentService contentService;

	public List<MovieCast> findAllByContent(Content content) throws NotFoundException {
		if (contentService.existById(content.getContentId())) {
			return movieCastRepository.findAllByContent(content);
		}
		throw new NotFoundException("Nije pronađen sadržaj sa id-em:" + content.getContentId());
	}

}
