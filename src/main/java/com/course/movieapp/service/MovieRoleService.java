package com.course.movieapp.service;

import com.course.movieapp.model.MovieRole;
import com.course.movieapp.model.Role;
import com.course.movieapp.repository.MovieRoleRepository;
import com.course.movieapp.repository.RoleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieRoleService {

    @Autowired
    MovieRoleRepository movieRoleRepository;

    // ulazni argument validirati na endpoint-u sa hibernate anotacijom
    public MovieRole save(MovieRole movieRole) {
        return movieRoleRepository.save(movieRole);
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

    public void delete(MovieRole movieRole) throws NotFoundException {
        if (movieRoleRepository.existsById(movieRole.getMovieRoleId())) {
            movieRoleRepository.delete(movieRole);
        }
        throw new NotFoundException("Nije pronađena uloga sa id-em:" + movieRole.getMovieRoleId());
    }
}
