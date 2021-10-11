package com.course.movieapp.service;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.ContentCommentDto;
import com.course.movieapp.dto.ContentRatingDto;
import com.course.movieapp.dto.EditCommentDto;
import com.course.movieapp.dto.FavoriteContentDto;
import com.course.movieapp.dto.ForgotPasswordDto;
import com.course.movieapp.dto.ResetPasswordDto;
import com.course.movieapp.dto.ReviewDto;
import com.course.movieapp.dto.UserDto;
import com.course.movieapp.exception.RegistrationException;
import com.course.movieapp.exception.TokenExpiredException;
import com.course.movieapp.model.Content;
import com.course.movieapp.model.PasswordResetToken;
import com.course.movieapp.model.Review;
import com.course.movieapp.model.ReviewKey;
import com.course.movieapp.model.Role;
import com.course.movieapp.model.User;
import com.course.movieapp.model.UserRole;
import com.course.movieapp.model.UserRoleKey;
import com.course.movieapp.repository.ContentRepository;
import com.course.movieapp.repository.PasswordResetTokenRepository;
import com.course.movieapp.repository.ReviewRepository;
import com.course.movieapp.repository.RoleRepository;
import com.course.movieapp.repository.UserRepository;
import com.course.movieapp.repository.UserRoleRepository;
import com.course.movieapp.utils.SecurityUtils;

import javassist.NotFoundException;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	PasswordResetTokenService passwordResetTokenService;

	@Autowired
	ContentService contentService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	ContentRepository contentRepository;

	@Autowired
	ContentCommentService contentCommentService;

	@Value("${server.port}")
	int serverPort;

	public User save(UserDto userDto) throws RegistrationException {
		if (!userRepository.existsByEmail(userDto.getEmail())
				&& !userRepository.existsByUsername(userDto.getUserName())) {
			User user = userRepository.save(buildUserFromDto(userDto));
			userDto.getRoleIds().forEach(roleId -> {
				UserRoleKey key = new UserRoleKey(user.getUserId(), roleId);
				Role role = roleRepository.getById(roleId);
				userRoleRepository.save(new UserRole(key, user, role));
			});
			return userRepository.save(user);
		}

		throw new RegistrationException("E-mail i username moraju biti jedinstveni!");
	}

	public User findById(int id) throws NotFoundException {
		return userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen korisnik sa id-em:" + id));
	}

	public User update(UserDto userDto) throws NotFoundException {
		if (userRepository.existsById(userDto.getUserId())) {
			userRoleRepository.deleteByUser(findById(userDto.getUserId()));
			User user = userRepository.save(buildUserFromDto(userDto));
			userDto.getRoleIds().forEach(roleId -> {
				UserRoleKey key = new UserRoleKey(user.getUserId(), roleId);
				Role role = roleRepository.getById(roleId);
				userRoleRepository.save(new UserRole(key, user, role));
			});

			return user;
		}
		throw new NotFoundException("Nije pronađen korisnik sa id-em:" + userDto.getUserId());
	}

	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void delete(int id) throws NotFoundException {
		User user = findById(id);
		user.setActive(false);
		userRepository.save(user);
	}

	public void forgotPassword(ForgotPasswordDto forgotPasswordDto) throws NotFoundException {
		User user = userRepository.findByEmail(forgotPasswordDto.getEmail()).orElseThrow(
				() -> new NotFoundException("Nije pronađen korisnik sa email-om:" + forgotPasswordDto.getEmail()));
		String token = UUID.randomUUID().toString();
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUser(user);
		passwordResetToken.setToken(token);
		passwordResetTokenRepository.save(passwordResetToken);

		String message = "http://" + InetAddress.getLoopbackAddress().getHostAddress() + ":" + serverPort
				+ "/user/reset-password?token=" + token;
		emailService.sendSimpleMessage(user.getEmail(), "MovieApp - resetovanje lozinke", message);
	}

	public User resetPassword(String token, ResetPasswordDto resetPasswordDto)
			throws NotFoundException, TokenExpiredException {
		PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);
		passwordResetTokenService.isTokenExpired(token);

		User user = passwordResetToken.getUser();
		user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
		userRepository.save(user);

		passwordResetToken.setExpiryDate(LocalDateTime.now());
		passwordResetTokenRepository.save(passwordResetToken);

		return user;
	}

	// korisnika mozemo pokupiti iz security context holdera
	public void rateTheContent(ContentRatingDto contentRatingDto) throws NotFoundException {
		Content content = contentService.findById(contentRatingDto.getContentId());
		User user = findById(contentRatingDto.getUserId());
		ReviewKey key = new ReviewKey(user.getUserId(), content.getContentId());
		if (reviewService.ifExistByReviewKey(key)) {
			Review review = reviewService.findById(content.getContentId(), user.getUserId());
			reviewService.update(new ReviewDto(contentRatingDto.getUserId(), contentRatingDto.getContentId(),
					contentRatingDto.getRating(), review.isFavourite()));
			content.setRating(calculateContentRating(content));
			contentRepository.save(content);
		} else {
			Review review = new Review(key, user, content, contentRatingDto.getRating(), false);
			reviewRepository.save(review);
			content.setRating(calculateContentRating(content));
			contentRepository.save(content);
		}
	}

	// korisnika mozemo pokupiti iz security context holder-a
	public void favourTheContent(FavoriteContentDto faovurContentDto) throws NotFoundException {
		Content content = contentService.findById(faovurContentDto.getContentId());
		User user = findById(faovurContentDto.getUserId());
		ReviewKey key = new ReviewKey(user.getUserId(), content.getContentId());
		if (reviewService.ifExistByReviewKey(key)) {
			Review review = reviewService.findById(content.getContentId(), user.getUserId());
			reviewService.update(new ReviewDto(faovurContentDto.getUserId(), faovurContentDto.getContentId(),
					review.getRating(), faovurContentDto.isFavourite()));
		} else {
			Review review = new Review(key, user, content, 0, faovurContentDto.isFavourite());
			reviewRepository.save(review);
		}

	}

	public void commentTheContent(ContentCommentDto contentCommentDto) throws NotFoundException {
		contentCommentService.save(contentCommentDto);
	}

	public void editComment(EditCommentDto editCommentDto) throws NotFoundException {
		contentCommentService.edit(editCommentDto);
	}

	public void deleteComment(int id) throws NotFoundException {
		contentCommentService.delete(id);
	}

	public List<Content> getFavouriteContent(int contentTypeId) {
		String userName = SecurityUtils.getUsername();
		User user = findUserByUsername(userName).get();
		List<Content> contents = reviewService.findByUserAndFavourite(user, true).stream()
				.filter(e -> e.getContent().getContentType().getContentTypeId() == contentTypeId)
				.map(e -> e.getContent()).collect(Collectors.toList());

		return contents;
	}

	private float calculateContentRating(Content content) {
		int sum = 0;
		List<Review> reviews = reviewService.findByContent(content).stream().filter(e -> e.getRating() > 0)
				.collect(Collectors.toList());
		int size = reviews.size();
		for (int i = 0; i < reviews.size(); i++) {
			sum += reviews.get(i).getRating();
		}

		return (sum / size);
	}

	private User buildUserFromDto(UserDto userDto) {
		User user = new User();
		if (userDto.getUserId() != null)
			user.setUserId(userDto.getUserId());
		user.setAdress(userDto.getAdress());
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setUsername(userDto.getUserName());

		return user;
	}

}
