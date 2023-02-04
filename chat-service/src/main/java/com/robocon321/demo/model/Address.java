package com.robocon321.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.Data;

@Data
@Document
public class Address {
	@Id
	private String id;
	private String street;
	private String city;

	@DocumentReference
	private List<Person> persons;

	public Address(String street, String city) {
		this.street = street;
		this.city = city;
	}
	
	
}