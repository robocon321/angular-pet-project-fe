package com.robocon321.demo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.robocon321.demo.dto.response.UserResponse;
import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public Page<UserResponse> getPage(Pageable pageable, Predicate predicate) {
		Page<User> users = userRepository.findAll(predicate, pageable);
		Page<UserResponse> response = pageDocumentToDTO(users);
		return response;
	}
	
	private Page<UserResponse> pageDocumentToDTO(Page<User> page) {
		return page.map(blog -> documentToDTO(blog));
	}
	
	private UserResponse documentToDTO(User user) {
		UserResponse responseDTO = new UserResponse();
		BeanUtils.copyProperties(user, responseDTO);
		return responseDTO;
	}
}
