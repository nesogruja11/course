package com.course.movieapp.controller;

import com.course.movieapp.dto.MovieRoleDto;
import com.course.movieapp.dto.RoleDto;
import com.course.movieapp.model.MovieRole;
import com.course.movieapp.model.Role;
import com.course.movieapp.service.MovieRoleService;
import com.course.movieapp.service.RoleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie-role")
public class MovieRoleController {

    @Autowired
    MovieRoleService movieRoleService;

    @GetMapping
    private MovieRole findById(@RequestParam int id) throws NotFoundException {
        return movieRoleService.findById(id);
    }

    @PostMapping("/save")
    private MovieRole save(@RequestBody MovieRoleDto movieRoleDto) {
        return movieRoleService.save(movieRoleDto);
    }

    @PutMapping("/update")
    private MovieRole update(@RequestBody MovieRole movieRole) throws NotFoundException {
        return movieRoleService.update(movieRole);
    }

    @DeleteMapping("/delete")
    private void delete(@RequestBody MovieRole movieRole) throws NotFoundException {
        movieRoleService.delete(movieRole);
    }
}
