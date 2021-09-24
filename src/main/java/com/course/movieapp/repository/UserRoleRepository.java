package com.course.movieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.course.movieapp.model.User;
import com.course.movieapp.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

	@Transactional
	@Modifying
	public void deleteByUser(User user);
}
