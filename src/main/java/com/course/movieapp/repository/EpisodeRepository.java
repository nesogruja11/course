package com.course.movieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.course.movieapp.model.Episode;
import com.course.movieapp.model.Season;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {

	@Transactional
	@Modifying
	void deleteBySeason(Season season);
}
