package com.course.movieapp.repository;

import com.course.movieapp.model.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.movieapp.model.Content;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {

    List<Content> findAllByOrderByRatingDesc();

    List<Content> findAllByContentTypeOrderByRatingDesc(ContentType contentType);

    List<Content> findAllByContentTypeOrderByReleaseDateDesc(ContentType contentType);
}
