package com.course.movieapp.repository;

import com.course.movieapp.model.Season;
import com.course.movieapp.model.SerieCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieCastRepository extends JpaRepository<SerieCast, Integer> {

    void deleteBySeason(Season season);
}
