package com.robocon321.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.robocon321.demo.model.Address;
import com.robocon321.demo.model.Person;

public interface AddressRepository extends MongoRepository<Address, String> {

}
