package com.robocon321.demo.fetcher;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robocon321.demo.dto.request.RegisterRequest;
import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.UserRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AuthDataFetcher {
	@Autowired
	private UserRepository userRepository;

	public DataFetcher<List<User>> getAllUsers() {
		return new DataFetcher<List<User>>() {
			@Override
			public List<User> get(DataFetchingEnvironment environment) throws Exception {
				return userRepository.findAll();
			}
		};
	}
	
	public DataFetcher<User> register() {
		return new DataFetcher<User>() {

			@Override
			public User get(DataFetchingEnvironment environment) throws Exception {
//				User user = new User();
//				BeanUtils.copyProperties(registerRequest, user);
//				return userRepository.save(user);
				return null;
			}
		};
	}
}
