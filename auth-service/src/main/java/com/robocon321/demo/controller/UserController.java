package com.robocon321.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robocon321.demo.resolver.UserResolver;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLException;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;

@RestController
public class UserController {
	private GraphQL graphQL = null;

	@Autowired
	public UserController(UserResolver userResolver) {
		GraphQLSchema schema = new GraphQLSchemaGenerator().withResolverBuilders(
				new AnnotatedResolverBuilder(),
				new PublicResolverBuilder("com.robocon321.demo"))
				.withOperationsFromSingleton(userResolver, UserResolver.class)
				.withValueMapperFactory(new JacksonValueMapperFactory()).generate();
		graphQL = GraphQL.newGraphQL(schema).build();
	}
		
	@PostMapping("/graphql")
	public Map<String,Object> execute(@RequestBody Map<String, String> request, HttpServletRequest raw)
			throws GraphQLException {
		ExecutionResult result = graphQL.execute(request.get("query"));
			return result.getData();
	
	}
}
