package com.course.movieapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.ContentGenre;
import com.course.movieapp.model.ContentGenreKey;
import com.course.movieapp.model.Genre;

@Repository
public interface ContentGenreRepository extends JpaRepository<ContentGenre, ContentGenreKey> {

	@Transactional
	@Modifying
	public void deleteByContent(Content content);

	List<ContentGenre> findAllByContent(Content content);

	List<ContentGenre> findAllByGenre(Genre genre);
}
