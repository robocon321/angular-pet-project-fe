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

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	HttpGraphQlClient httpGraphQlClient;

	@GetMapping("/all")
	public Mono<List<User>> getAll(@RequestParam(defaultValue = "id, email, password, fullName, birthday, gender") String fields) {
		var document = "query {" +
				"  allUsers {\n" +
						fields +
				"  }\n" +
				"}";
		return this.httpGraphQlClient.document(document)
				.retrieve("allUsers")
				.toEntityList(User.class);
	}

	
	@QueryMapping
	public List<User> allUsers() {
		return this.userRepository.findAll();
	}

}
