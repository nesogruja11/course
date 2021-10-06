package com.course.movieapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.GenreDto;
import com.course.movieapp.model.Genre;
import com.course.movieapp.repository.GenreRepository;

import javassist.NotFoundException;

@Service
public class GenreService {

	@Autowired
	GenreRepository genreRepository;

	public Genre save(GenreDto genreDto) {
		return genreRepository.save(createGenreFromDto(genreDto));
	}

	public Genre findById(int id) throws NotFoundException {
		return genreRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen žanr sa id-em:" + id));
	}

	public Genre update(Genre genre) throws NotFoundException {
		if (genreRepository.existsById(genre.getGenreId())) {
			return genreRepository.save(genre);
		}
		throw new NotFoundException("Nije pronađen žanr sa id-em:" + genre.getGenreId());
	}

	public void delete(int genreId) throws NotFoundException {
		if (genreRepository.existsById(genreId)) {
			genreRepository.deleteById(genreId);
		} else {
			throw new NotFoundException("Nije pronađen žanr sa id-em:" + genreId);
		}
	}

	private Genre createGenreFromDto(GenreDto genreDto) {
		Genre genre = new Genre();
		genre.setName(genreDto.getName());
		return genre;
	}
}
