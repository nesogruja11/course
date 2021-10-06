package com.course.movieapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.ReviewDto;
import com.course.movieapp.model.Content;
import com.course.movieapp.model.Review;
import com.course.movieapp.model.ReviewKey;
import com.course.movieapp.repository.ReviewRepository;

import javassist.NotFoundException;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	ContentService contentService;

	@Autowired
	UserService userService;

	// omoguciti da se prilikom save, update i delete metoda updateuje i ukupan
	// rating za neki content ! ! !

	public Review save(ReviewDto reviewDto) throws NotFoundException {
		return reviewRepository.save(buildReviewFromDto(reviewDto));
	}

	public Review update(ReviewDto reviewDto) throws NotFoundException {
		if (reviewRepository.existsByReviewKey(new ReviewKey(reviewDto.getUserId(), reviewDto.getContentId()))) {
			return reviewRepository.save(buildReviewFromDto(reviewDto));
		}
		throw new NotFoundException("Nije pronađen utisak za content sa id-em:" + reviewDto.getContentId()
				+ " od korisnika sa id-em:" + reviewDto.getUserId());
	}

	// dovoljno je poslati contentId i userId za brisanje review-a
	public void delete(ReviewDto reviewDto) throws NotFoundException {
		ReviewKey key = new ReviewKey(reviewDto.getUserId(), reviewDto.getContentId());
		if (reviewRepository.existsByReviewKey(key)) {
			reviewRepository.deleteByReviewKey(key);
		}
		throw new NotFoundException("Nije pronađen utisak za content sa id-em:" + reviewDto.getContentId()
				+ " od korisnika sa id-em:" + reviewDto.getUserId());
	}

	public Review findById(int contentId, int userId) throws NotFoundException {
		ReviewKey key = new ReviewKey(userId, contentId);
		if (reviewRepository.existsByReviewKey(key)) {
			return reviewRepository.findByReviewKey(key);
		}
		throw new NotFoundException(
				"Nije pronađen utisak za content sa id-em:" + contentId + " od korisnika sa id-em:" + userId);
	}

	public boolean ifExistByReviewKey(ReviewKey reviewKey) {
		if (reviewRepository.existsByReviewKey(reviewKey)) {
			return true;
		} else {
			return false;
		}
	}

	public List<Review> findByContent(Content content) {
		return reviewRepository.findByContent(content);
	}

	private Review buildReviewFromDto(ReviewDto reviewDto) throws NotFoundException {
		Review review = new Review();
		review.setReviewKey(new ReviewKey(reviewDto.getUserId(), reviewDto.getContentId()));
		review.setContent(contentService.findById(reviewDto.getContentId()));
		review.setFavourite(reviewDto.isFavourite());
		review.setRating(reviewDto.getRating());
		review.setUser(userService.findById(reviewDto.getUserId()));

		return review;
	}
}
