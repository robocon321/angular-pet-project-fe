package com.robocon321.demo.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.robocon321.demo.dto.request.RegisterRequest;
import com.robocon321.demo.exception.ConflictException;
import com.robocon321.demo.model.ERole;
import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.UserRepository;
import com.robocon321.demo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private UserRepository userRepository;
	
	@Value("classpath:graphql/user.graphqls")
	private Resource userResource;


	@Override
	public void save(RegisterRequest registerRequest, ERole role) {
		User user = new User();
		BeanUtils.copyProperties(registerRequest, user);
		user.setRole(ERole.CLIENT);
		try {
			userRepository.save(user);			
		} catch(Exception ex) {
			// cannot catch MongoWriteException
			
			throw new ConflictException("Email " + registerRequest.getEmail() + " already exists");
		}
	}
}
