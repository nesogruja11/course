package com.course.movieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.movieapp.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
