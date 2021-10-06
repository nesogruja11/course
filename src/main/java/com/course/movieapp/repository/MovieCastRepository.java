package com.course.movieapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.MovieCast;
import com.course.movieapp.model.MovieCastKey;

@Repository
public interface MovieCastRepository extends JpaRepository<MovieCast, MovieCastKey> {

	@Transactional
	@Modifying
	void deleteByContent(Content content);

	List<MovieCast> findAllByContent(Content content);

}
