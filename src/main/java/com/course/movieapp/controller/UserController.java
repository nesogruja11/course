package com.course.movieapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.movieapp.dto.UserDto;
import com.course.movieapp.model.User;
import com.course.movieapp.service.UserService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	private User findById(@RequestParam int id) throws NotFoundException {
		return userService.findById(id);
	}

	@PostMapping("/add")
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
}
