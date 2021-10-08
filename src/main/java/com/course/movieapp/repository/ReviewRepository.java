package com.course.movieapp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.Review;
import com.course.movieapp.model.ReviewKey;
import com.course.movieapp.model.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewKey> {

	@Transactional
	@Modifying
	void deleteByContent(Content content);

	@Transactional
	@Modifying
	void deleteByReviewKey(ReviewKey reviewKey);

	Review findByReviewKey(ReviewKey reviewKey);

	boolean existsByReviewKey(ReviewKey reviewKey);

	List<Review> findByContent(Content content);

	List<Review> findByUserAndFavourite(User user, boolean favourite);
}
