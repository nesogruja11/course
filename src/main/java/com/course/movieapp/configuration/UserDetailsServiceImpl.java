package com.course.movieapp.configuration;

import com.course.movieapp.model.AuthUserDetails;
import com.course.movieapp.model.User;
import com.course.movieapp.model.UserRole;
import com.course.movieapp.repository.UserRoleRepository;
import com.course.movieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("tu sam");
        Optional<User> user = userService.findUserByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Nije pronađen korisnik sa username-om:" + username));
        List<UserRole> userRoles = userRoleRepository.findByUser(user.get());

        return new AuthUserDetails(user, userRoles);
    }

}