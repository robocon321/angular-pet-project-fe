package com.robocon321.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;
import com.robocon321.demo.dto.response.UserResponse;
import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.UserRepository;
import com.robocon321.demo.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public Page<UserResponse> getPage(@QuerydslPredicate(root = User.class) Predicate predicate,
			@PageableDefault(page = 0, size = 3) @SortDefault.SortDefaults({
					@SortDefault(sort = "email", direction = Sort.Direction.DESC)}) Pageable pageable) {
		return userService.getPage(pageable, predicate);
	}	
}
