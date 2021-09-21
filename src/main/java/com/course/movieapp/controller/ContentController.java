package com.course.movieapp.controller;

import com.course.movieapp.dto.ContentDto;
import com.course.movieapp.model.Content;
import com.course.movieapp.service.ContentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    ContentService contentService;

    @GetMapping
    public Content findById(@RequestParam int id) throws NotFoundException {
        return contentService.findById(id);
    }

    @PostMapping("/add")
    public Content save(@RequestBody ContentDto contentDto) throws NotFoundException {
        return contentService.save(contentDto);
    }
}
