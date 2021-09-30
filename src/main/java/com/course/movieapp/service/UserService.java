package com.course.movieapp.service;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.course.movieapp.dto.ForgotPasswordDto;
import com.course.movieapp.dto.ResetPasswordDto;
import com.course.movieapp.exception.TokenExpiredException;
import com.course.movieapp.model.*;
import com.course.movieapp.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.UserDto;
import com.course.movieapp.repository.RoleRepository;
import com.course.movieapp.repository.UserRepository;
import com.course.movieapp.repository.UserRoleRepository;

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

    @Value("${server.port}")
    int serverPort;

    // ulazni argument validirati na endpoint-u sa hibernate anotacijom
    // password mora biti hashovan kada se doda security
    public User save(UserDto userDto) {
        User user = userRepository.save(buildUserFromDto(userDto));
        userDto.getRoleIds().forEach(roleId -> {
            UserRoleKey key = new UserRoleKey(user.getUserId(), roleId);
            Role role = roleRepository.getById(roleId);
            userRoleRepository.save(new UserRole(key, user, role));
        });
        return userRepository.save(user);
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
        User user = userRepository.findByEmail(forgotPasswordDto.getEmail()).orElseThrow(() -> new NotFoundException("Nije pronađen korisnik sa email-om:" + forgotPasswordDto.getEmail()));
		String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetTokenRepository.save(passwordResetToken);

        String message = "http://" + InetAddress.getLoopbackAddress().getHostAddress()+":"+serverPort+"/user/reset-password?token="+token;
        emailService.sendSimpleMessage(user.getEmail(), "MovieApp - resetovanje lozinke", message);
    }

    public User resetPassword(String token, ResetPasswordDto resetPasswordDto) throws NotFoundException, TokenExpiredException {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);
        passwordResetTokenService.isTokenExpired(token);

        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        userRepository.save(user);

        passwordResetToken.setExpiryDate(LocalDateTime.now());
        passwordResetTokenRepository.save(passwordResetToken);

        return user;
    }

    // password mora biti hashovan kada se doda security
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
