package com.robocon321.demo.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.robocon321.demo.model.Person;

public interface PersonRepository extends MongoRepository<Person, String> {
	List<Person> findByAddresses_StreetContaining(String street);
	List<Person> findByAddresses_Id(String addressId);
}