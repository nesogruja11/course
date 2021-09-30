package com.course.movieapp.service;

import com.course.movieapp.exception.TokenExpiredException;
import com.course.movieapp.model.PasswordResetToken;
import com.course.movieapp.repository.PasswordResetTokenRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PasswordResetTokenService {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetToken findByToken(String token) throws NotFoundException {
        return passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new NotFoundException("Nevalidan token za reset lozinke!"));
    }

    public void isTokenExpired(String token) throws NotFoundException, TokenExpiredException {
        PasswordResetToken passwordResetToken = findByToken(token);
        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException(passwordResetToken.getExpiryDate().toString());
        }
    }
}
