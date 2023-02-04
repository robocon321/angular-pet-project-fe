package com.robocon321.demo.model;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Document
public class Person {
	@Id
	private String id;
	private String name;
	@DocumentReference
	private List<Address> addresses;
	public Person(String name, List<Address> addresses) {
		this.name = name;
		this.addresses = addresses;
	}
}