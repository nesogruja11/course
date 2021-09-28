package com.course.movieapp.repository;

import com.course.movieapp.model.MoviePeople;
import com.course.movieapp.model.MoviePeopleRole;
import com.course.movieapp.model.MoviePeopleRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MoviePeopleRoleRepository extends JpaRepository<MoviePeopleRole, MoviePeopleRoleKey> {

    @Transactional
    @Modifying
    void deleteByMoviePeople(MoviePeople moviePeople);
}
