package com.course.movieapp.repository;

import com.course.movieapp.model.ContentGenre;
import com.course.movieapp.model.ContentGenreKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentGenreRepository extends JpaRepository<ContentGenre, ContentGenreKey> {
}
