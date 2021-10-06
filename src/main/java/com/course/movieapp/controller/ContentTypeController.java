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

import com.course.movieapp.dto.ContentTypeDto;
import com.course.movieapp.model.ContentType;
import com.course.movieapp.service.ContentTypeService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/content-type")
class ContentTypeController {

	@Autowired
	ContentTypeService contentTypeService;

	@GetMapping
	public ContentType findById(@RequestParam int id) throws NotFoundException {
		return contentTypeService.findById(id);
	}

	@PostMapping("/save")
	public ContentType save(@Valid @RequestBody ContentTypeDto contentTypeDto) throws NotFoundException {
		return contentTypeService.save(contentTypeDto);
	}

	@PutMapping("/update")
	private ContentType update(@Valid @RequestBody ContentType contentType) throws NotFoundException {
		return contentTypeService.update(contentType);
	}

	@DeleteMapping("/delete")
	private void delete(@RequestParam int id) throws NotFoundException {
		contentTypeService.delete(id);
	}

}
