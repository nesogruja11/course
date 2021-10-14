package com.course.movieapp.utils;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.course.movieapp.model.User;
import com.course.movieapp.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	private LocalDateTime expirationDate;

	@Autowired
	private UserRepository userRepository;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
		claims.put("userId", user.get().getUserId());
		claims.put("firstName", user.get().getFirstName());
		claims.put("lastName", user.get().getLastName());
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {

		// @formatter:off
		
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				// .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.setExpiration(setTokenExpirationDate(1))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
		
		// @formatter:on
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private Date setTokenExpirationDate(int years) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, years);

		return calendar.getTime();
	}
}