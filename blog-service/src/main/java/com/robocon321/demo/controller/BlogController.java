package com.robocon321.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robocon321.demo.dto.request.BlogRequest;
import com.robocon321.demo.dto.response.BlogResponse;
import com.robocon321.demo.service.impl.BlogService;

@RestController
@RequestMapping("api/blog")
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@PostMapping
	public BlogResponse save(@ModelAttribute BlogRequest blogRequest) throws IOException {
		return blogService.save(blogRequest);
	}
	
	@GetMapping
	public String get() {
		return "Hello world";
	}
}
