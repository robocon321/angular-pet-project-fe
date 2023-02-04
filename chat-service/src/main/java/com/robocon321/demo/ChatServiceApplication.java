package com.robocon321.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.robocon321.demo.model.Address;
import com.robocon321.demo.model.Person;
import com.robocon321.demo.repository.AddressRepository;
import com.robocon321.demo.repository.PersonRepository;

@SpringBootApplication
public class ChatServiceApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ChatServiceApplication.class, args);
	}

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public void run(String... args) throws Exception {
//		Address address1 = new Address("1 Main St", "New York");
//		Address address2 = new Address("2 Main St", "Los Angeles");
//		addressRepository.saveAll(Arrays.asList(address1, address2));
//
//		Person person1 = new Person("John Doe", Arrays.asList(address1, address2));
//		Person person2 = new Person("Jane Doe", Arrays.asList(address1));
//		personRepository.saveAll(Arrays.asList(person1, person2));
	}
}
