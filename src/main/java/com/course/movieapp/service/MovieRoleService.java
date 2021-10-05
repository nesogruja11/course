package com.course.movieapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.MovieRoleDto;
import com.course.movieapp.model.MovieRole;
import com.course.movieapp.repository.MovieRoleRepository;

import javassist.NotFoundException;

@Service
public class MovieRoleService {

	@Autowired
	MovieRoleRepository movieRoleRepository;

	// ulazni argument validirati na endpoint-u sa hibernate anotacijom
	public MovieRole save(MovieRoleDto movieRoleDto) {
		return movieRoleRepository.save(buildMovieRoleFromDto(movieRoleDto));
	}

	public MovieRole findById(int id) throws NotFoundException {
		return movieRoleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađena uloga sa id-em:" + id));
	}

	public MovieRole update(MovieRole movieRole) throws NotFoundException {
		if (movieRoleRepository.existsById(movieRole.getMovieRoleId())) {
			return movieRoleRepository.save(movieRole);
		}
		throw new NotFoundException("Nije pronađena uloga sa id-em:" + movieRole.getMovieRoleId());
	}

	public void delete(int movieRoleId) throws NotFoundException {
		if (movieRoleRepository.existsById(movieRoleId)) {
			movieRoleRepository.deleteById(movieRoleId);
		} else {
			throw new NotFoundException("Nije pronađena uloga sa id-em:" + movieRoleId);
		}
	}

	private MovieRole buildMovieRoleFromDto(MovieRoleDto movieRoleDto) {
		MovieRole movieRole = new MovieRole();
		movieRole.setName(movieRoleDto.getName());

		return movieRole;
	}
}
