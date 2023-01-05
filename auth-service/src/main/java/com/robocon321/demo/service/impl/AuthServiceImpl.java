package com.robocon321.demo.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.robocon321.demo.dto.request.RegisterRequest;
import com.robocon321.demo.exception.ConflictException;
import com.robocon321.demo.fetcher.AuthDataFetcher;
import com.robocon321.demo.model.ERole;
import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.UserRepository;
import com.robocon321.demo.service.AuthService;
import com.robocon321.demo.util.DateScalarUtil;

import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthDataFetcher authDataFetcher;

	@Autowired
	private UserRepository userRepository;
	
	@Value("classpath:graphql/user.graphqls")
	private Resource userResource;

	private GraphQL graphQL;

	@PostConstruct
	private void loadSchema() throws IOException {
		File schemaFile = userResource.getFile();
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildRuntimeWiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}

	private RuntimeWiring buildRuntimeWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.scalar(DateScalarUtil.get())
				.type("Query", typeWiring -> typeWiring.dataFetcher("allUsers", authDataFetcher.getAllUsers()))
//				.type("Mutation", typeWiring -> typeWiring.dataFetcher("saveUser", authDataFetcher.register(RegisterRequest.class)))
				.build();
	}

	public GraphQL getGraphQL() {
		return graphQL;
	}

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
