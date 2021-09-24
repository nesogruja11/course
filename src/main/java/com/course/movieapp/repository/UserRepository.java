package com.course.movieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.movieapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
