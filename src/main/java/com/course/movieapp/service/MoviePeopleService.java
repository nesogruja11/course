package com.course.movieapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.MoviePeopleDto;
import com.course.movieapp.dto.MoviePeopleUpdateDto;
import com.course.movieapp.model.MoviePeople;
import com.course.movieapp.model.MoviePeopleRole;
import com.course.movieapp.model.MoviePeopleRoleKey;
import com.course.movieapp.model.MovieRole;
import com.course.movieapp.repository.MoviePeopleRepository;
import com.course.movieapp.repository.MoviePeopleRoleRepository;
import com.course.movieapp.repository.MovieRoleRepository;

import javassist.NotFoundException;

@Service
public class MoviePeopleService {

	@Autowired
	MoviePeopleRepository moviePeopleRepository;

	@Autowired
	MovieRoleRepository movieRoleRepository;

	@Autowired
	MoviePeopleRoleRepository moviePeopleRoleRepository;

	// ulazni argument validirati na endpoint-u sa hibernate anotacijom
	// password mora biti hashovan kada se doda security
	public MoviePeople save(MoviePeopleDto moviePeopleDto) {
		MoviePeople moviePeople = moviePeopleRepository.save(createMoviePeopleFromDto(moviePeopleDto));
		moviePeopleDto.getMoviePeopleRoleIds().forEach(roleId -> {
			MoviePeopleRoleKey key = new MoviePeopleRoleKey(moviePeople.getMoviePeopleId(), roleId);
			MovieRole role = movieRoleRepository.getById(roleId);
			moviePeopleRoleRepository.save(new MoviePeopleRole(key, role, moviePeople));
		});
		return moviePeopleRepository.save(moviePeople);
	}

	public MoviePeople findById(int id) throws NotFoundException {
		return moviePeopleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen akter sa id-em:" + id));
	}

	public MoviePeople update(MoviePeopleUpdateDto moviePeopleUpdateDto) throws NotFoundException {
		if (moviePeopleRepository.existsById(moviePeopleUpdateDto.getMoviePeopleId())) {
			moviePeopleRoleRepository.deleteByMoviePeople(findById(moviePeopleUpdateDto.getMoviePeopleId()));
			MoviePeople moviePeople = moviePeopleRepository.save(updateMoviePeopleFromDto(moviePeopleUpdateDto));
			moviePeopleUpdateDto.getMoviePeopleRoleIds().forEach(roleId -> {
				MoviePeopleRoleKey key = new MoviePeopleRoleKey(moviePeopleUpdateDto.getMoviePeopleId(), roleId);
				MovieRole role = movieRoleRepository.getById(roleId);
				moviePeopleRoleRepository.save(new MoviePeopleRole(key, role, moviePeople));
			});

			return moviePeople;
		}
		throw new NotFoundException("Nije pronađen akter sa id-em:" + moviePeopleUpdateDto.getMoviePeopleId());
	}

	public void delete(int moviePeopleId) throws NotFoundException {
		if (moviePeopleRepository.existsById(moviePeopleId)) {
			MoviePeople moviePeople = findById(moviePeopleId);
			moviePeople.setActive(false);
			moviePeopleRepository.save(moviePeople);
		} else {
			throw new NotFoundException("Nije pronađen akter sa id-em:" + moviePeopleId);
		}
	}

	private MoviePeople copyFromDtoToObject(MoviePeople moviePeople, MoviePeopleDto moviePeopleDto) {
		moviePeople.setBirthDate(moviePeopleDto.getBirthDate());
		moviePeople.setFirstName(moviePeopleDto.getFirstName());
		moviePeople.setGender(moviePeopleDto.getGender());
		moviePeople.setLastName(moviePeopleDto.getLastName());

		return moviePeople;
	}

	private MoviePeople updateMoviePeopleFromDto(MoviePeopleUpdateDto moviePeopleUpdateDto) {
		MoviePeople moviePeople = new MoviePeople();
		copyFromDtoToObject(moviePeople, moviePeopleUpdateDto);
		moviePeople.setMoviePeopleId(moviePeopleUpdateDto.getMoviePeopleId());
		return moviePeople;
	}

	private MoviePeople createMoviePeopleFromDto(MoviePeopleDto moviePeopleDto) {
		MoviePeople moviePeople = new MoviePeople();
		copyFromDtoToObject(moviePeople, moviePeopleDto);
		return moviePeople;
	}

}
