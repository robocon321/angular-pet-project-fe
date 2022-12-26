package com.robocon321.demo.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class User {
	@Id
	private String id;
	private String email;
	private String password;
	private String fullName;
	private LocalDate birthday;
	private Boolean gender;
}
