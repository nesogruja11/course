package com.course.movieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.movieapp.model.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {

}
