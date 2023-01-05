package com.robocon321.demo.service;

import com.robocon321.demo.dto.request.RegisterRequest;
import com.robocon321.demo.model.ERole;

import graphql.GraphQL;

public interface AuthService {
	GraphQL getGraphQL();
	void save(RegisterRequest registerRequest, ERole role);
}
