package com.course.movieapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }

    @GetMapping("/hash-map")
    public HashMap<String, String> hashMap(){
        Map<String, String> map = new HashMap<>();
        map.put("name1","Goran");
        map.put("name2","Ivan");
        return (HashMap<String, String>) map;
    }
}
