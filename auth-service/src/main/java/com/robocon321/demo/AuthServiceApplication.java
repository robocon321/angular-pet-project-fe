package com.robocon321.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpGraphQlClient;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
	

//	@Bean
//	CommandLineRunner runner(UserRepository repository) {
//		return args -> {
//			User user = new User();
//			user.setEmail("robocon321n@gmail.com");
//			user.setFullName("Nguyễn Tata");
//			user.setBirthday(new Date());
//			user.setGender(true);
//			user.setPassword("ksahef9882389");
//			repository.save(user);
//		};
//	}
}
