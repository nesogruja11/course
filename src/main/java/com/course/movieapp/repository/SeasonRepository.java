package com.course.movieapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {

	List<Season> findAllByContent(Content content);
}
