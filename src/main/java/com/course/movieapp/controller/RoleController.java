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

import com.course.movieapp.model.Role;
import com.course.movieapp.service.RoleService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	RoleService roleService;

	@GetMapping
	private Role findById(@RequestParam int id) throws NotFoundException {
		return roleService.findById(id);
	}

	@PostMapping("/add")
	private Role save(@RequestBody Role role) {
		return roleService.save(role);
	}

	@PutMapping("/update")
	private Role update(@RequestBody Role role) throws NotFoundException {
		return roleService.update(role);
	}

	@DeleteMapping("/delete")
	private void delete(@RequestBody Role role) throws NotFoundException {
		roleService.delete(role);
	}
}
