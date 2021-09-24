package com.course.movieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.ContentGenre;
import com.course.movieapp.model.ContentGenreKey;

@Repository
public interface ContentGenreRepository extends JpaRepository<ContentGenre, ContentGenreKey> {

	@Transactional
	@Modifying
	public void deleteByContent(Content content);
}
