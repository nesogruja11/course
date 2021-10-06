package com.course.movieapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.movieapp.configuration.UserDetailsServiceImpl;
import com.course.movieapp.dto.ContentCommentDto;
import com.course.movieapp.dto.ContentRatingDto;
import com.course.movieapp.dto.FavoriteContentDto;
import com.course.movieapp.dto.ForgotPasswordDto;
import com.course.movieapp.dto.ResetPasswordDto;
import com.course.movieapp.dto.UserDto;
import com.course.movieapp.exception.TokenExpiredException;
import com.course.movieapp.model.AuthenticationRequest;
import com.course.movieapp.model.AuthenticationResponse;
import com.course.movieapp.model.User;
import com.course.movieapp.service.UserService;
import com.course.movieapp.util.JwtUtil;

import javassist.NotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@GetMapping
	private User findById(@RequestParam int id) throws NotFoundException {
		return userService.findById(id);
	}

	@PostMapping("/register")
	private User save(@RequestBody UserDto userDto) {
		return userService.save(userDto);
	}

	@PutMapping("/update")
	private User update(@RequestBody UserDto userDto) throws NotFoundException {
		return userService.update(userDto);
	}

	@DeleteMapping("/delete")
	private void delete(@RequestParam int id) throws NotFoundException {
		userService.delete(id);
	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (BadCredentialsException ex) {
			return new ResponseEntity<String>("Pogrešni kredencijali!", HttpStatus.BAD_REQUEST);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);

		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(jwt), HttpStatus.OK);
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) throws NotFoundException {
		userService.forgotPassword(forgotPasswordDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/reset-password")
	public User resetPassword(@RequestParam String token, @RequestBody ResetPasswordDto resetPasswordDto)
			throws NotFoundException, TokenExpiredException {
		return userService.resetPassword(token, resetPasswordDto);
	}

	@PostMapping("/rating")
	public void rateTheContent(@RequestBody ContentRatingDto contentRatingDto) throws NotFoundException {
		userService.rateTheContent(contentRatingDto);
	}

	@PostMapping("/favourite")
	public void favourTheContent(@RequestBody FavoriteContentDto faovurContentDto) throws NotFoundException {
		userService.favourTheContent(faovurContentDto);
	}

	@PostMapping("/comment")
	public void commentTheContent(@RequestBody ContentCommentDto contentCommentDto) throws NotFoundException {
		userService.commentTheContent(contentCommentDto);
	}
}
