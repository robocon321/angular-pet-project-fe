package com.robocon321.demo.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.robocon321.demo.fetcher.AuthDataFetcher;
import com.robocon321.demo.repository.UserRepository;
import com.robocon321.demo.service.AuthService;

import graphql.GraphQL;
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
				.type("Query", typeWiring -> 
					typeWiring.dataFetcher("allUsers", authDataFetcher))
				.build();
	}
	
	public GraphQL getGraphQL() {
		return graphQL;
	}
}
