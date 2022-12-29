package com.robocon321.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.UserRepository;
import com.robocon321.demo.service.AuthService;

import graphql.ExecutionResult;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserRepository userRepository;
		
	@Autowired
	private AuthService authService;

	@GetMapping("/all")
	public ExecutionResult getAll(@RequestParam(defaultValue = "id, email, password, fullName, birthday, gender") String fields) {
		var query = "query {" +
				"  allUsers {\n" +
						fields +
				"  }\n" +
				"}";
		ExecutionResult execute = authService.getGraphQL().execute(query);
		return execute;
	}

	
//	@QueryMapping
//	public List<User> allUsers() {
//		return this.userRepository.findAll();
//	}

}
