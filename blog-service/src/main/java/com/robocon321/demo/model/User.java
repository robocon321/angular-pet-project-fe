package com.robocon321.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	private String id;
	private String email;
	private String password;
	private ERole role;
	private List<Blog> blogs = new ArrayList<>();
}
