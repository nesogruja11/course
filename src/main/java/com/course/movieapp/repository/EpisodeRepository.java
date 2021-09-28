package com.course.movieapp.repository;

import com.course.movieapp.model.Episode;
import com.course.movieapp.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {

    void deleteBySeason(Season season);
}
