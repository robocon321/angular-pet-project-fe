package com.robocon321.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;
import com.robocon321.demo.dto.request.CreateBlogRequest;
import com.robocon321.demo.dto.request.UpdateBlogRequest;
import com.robocon321.demo.dto.response.BlogResponse;
import com.robocon321.demo.model.Blog;
import com.robocon321.demo.service.impl.BlogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/blog")
public class BlogController {
	
	@Autowired
	private BlogService blogService;

	@PostMapping
	public BlogResponse save(@Valid @ModelAttribute CreateBlogRequest blogRequest) throws IOException {
		return blogService.save(blogRequest);
	}

	@PutMapping
	public BlogResponse update(@Valid @ModelAttribute UpdateBlogRequest blogRequest) throws IOException {
		return blogService.update(blogRequest);
	}
	
	@GetMapping("sayHi")
	public String get() {
		return "Hello world";
	}

	@GetMapping
	public Page<BlogResponse> getPage(@QuerydslPredicate(root = Blog.class) Predicate predicate,
			@PageableDefault(page = 0, size = 10) @SortDefault.SortDefaults({
					@SortDefault(sort = "title", direction = Sort.Direction.DESC)}) Pageable pageable) {
		return blogService.getPage(pageable, predicate);
	}
	
	@DeleteMapping
	public void delete(@RequestBody String id) {
		blogService.delete(id);
	}
	
	@GetMapping("{id}")
	public BlogResponse getById(@PathVariable String id) {
		return blogService.getById(id);
	}
}
