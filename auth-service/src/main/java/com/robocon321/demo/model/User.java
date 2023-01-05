package com.robocon321.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Document
public class User {
	@Id
	private String id;

	@Indexed(unique = true)
	private String email;
	
	@NotBlank
	@Size(min = 4, max = 10)
	private String password;

	@NotNull
	private ERole role;
	
	private RefreshToken refreshToken;
	
}
