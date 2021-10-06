package com.course.movieapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.movieapp.dto.CountryDto;
import com.course.movieapp.model.Country;
import com.course.movieapp.service.CountryService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/country")
public class CountryController {

	@Autowired
	CountryService countryService;

	@GetMapping
	public Country findById(@RequestParam int id) throws NotFoundException {
		return countryService.findById(id);
	}

	@PostMapping("/save")
	public Country save(@Valid @RequestBody CountryDto countryDto) throws NotFoundException {
		return countryService.save(countryDto);
	}

	@PutMapping("/update")
	private Country update(@Valid @RequestBody Country country) throws NotFoundException {
		return countryService.update(country);
	}

	@DeleteMapping("/delete")
	private void delete(@RequestParam int id) throws NotFoundException {
		countryService.delete(id);
	}

}
