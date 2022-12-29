package com.robocon321.demo.fetcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.UserRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AuthDataFetcher implements DataFetcher<List<User>> {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> get(DataFetchingEnvironment environment) throws Exception {
		return userRepository.findAll();
	}

}
