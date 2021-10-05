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

import com.course.movieapp.dto.LanguageDto;
import com.course.movieapp.model.Language;
import com.course.movieapp.service.LanguageService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/language")
public class LanguageController {

	@Autowired
	LanguageService languageService;

	@GetMapping
	public Language findById(@RequestParam int id) throws NotFoundException {
		return languageService.findById(id);
	}

	@PostMapping("/save")
	public Language save(@RequestBody LanguageDto languageDto) throws NotFoundException {
		return languageService.save(languageDto);
	}

	@PutMapping("/update")
	private Language update(@RequestBody Language language) throws NotFoundException {
		return languageService.update(language);
	}

	@DeleteMapping("/delete")
	private void delete(@RequestParam int id) throws NotFoundException {
		languageService.delete(id);
	}

}
