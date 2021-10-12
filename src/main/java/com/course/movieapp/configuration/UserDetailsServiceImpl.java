package com.course.movieapp.configuration;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.course.movieapp.model.AuthUserDetails;
import com.course.movieapp.model.User;
import com.course.movieapp.model.UserRole;
import com.course.movieapp.repository.UserRoleRepository;
import com.course.movieapp.service.UserService;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserService userService;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = userService.findUserByUsername(username);
		user.orElseThrow(() -> new UsernameNotFoundException("Nije pronađen korisnik sa username-om:" + username));
		List<UserRole> userRoles = userRoleRepository.findByUser(user.get());

		AuthUserDetails auth = new AuthUserDetails(user, userRoles);
		return auth;
	}

}