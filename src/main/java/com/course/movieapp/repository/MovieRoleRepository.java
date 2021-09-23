package com.course.movieapp.repository;

import com.course.movieapp.model.MovieRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRoleRepository extends JpaRepository<MovieRole, Integer> {
}
