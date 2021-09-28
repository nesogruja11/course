package com.course.movieapp.repository;

import com.course.movieapp.model.Review;
import com.course.movieapp.model.ReviewKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    boolean existsByReviewKey(ReviewKey reviewKey);

    void deleteByReviewKey(ReviewKey reviewKey);

    Review findByReviewKey(ReviewKey reviewKey);
}
