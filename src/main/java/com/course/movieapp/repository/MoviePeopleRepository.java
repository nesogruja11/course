package com.course.movieapp.repository;

import com.course.movieapp.model.MoviePeople;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviePeopleRepository extends JpaRepository<MoviePeople, Integer> {
}
