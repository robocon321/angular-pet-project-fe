package com.robocon321.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpGraphQlClient;

@Configuration
public class HttpGraphQlClientConfig {
	@Bean
	public HttpGraphQlClient httpGraphQlClient() {
		return HttpGraphQlClient.builder().url("http://localhost:8080/graphql").build();
	}

}
