package com.robocon321.demo.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.UserRepository;

import io.leangen.graphql.annotations.GraphQLQuery;

@Component
public class UserResolver {
	@Autowired
	private UserRepository userRepository;
	
    @GraphQLQuery(name = "getAllUsers", description = "Get all users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
