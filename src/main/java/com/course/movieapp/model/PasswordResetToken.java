package com.course.movieapp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_id")
	private int tokenId;

	@Column(name = "token")
	private String token;

	@OneToOne(targetEntity = User.class)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	@Column(name = "expiry_date")
	private LocalDateTime expiryDate = LocalDateTime.now().plusDays(1);
}
