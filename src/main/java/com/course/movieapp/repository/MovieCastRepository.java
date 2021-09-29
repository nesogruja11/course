package com.course.movieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.MovieCast;
import com.course.movieapp.model.MovieCastKey;

@Repository
public interface MovieCastRepository extends JpaRepository<MovieCast, MovieCastKey> {

	void deleteByContent(Content content);

}
