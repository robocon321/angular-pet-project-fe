package com.robocon321.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robocon321.demo.model.Person;
import com.robocon321.demo.repository.PersonRepository;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

  @Autowired
  private PersonRepository personRepository;

  @GetMapping("/street/{street}")
  public List<Person> findByAddressStreet(@PathVariable String street) {
	List<Person> people = findByStreetContaining(street);
//	List<Person> people = personRepository.findByAddresses_StreetContaining(street);
    return people;
  }

  @GetMapping("/addressId/{id}")
  public List<Person> findByAddressId(@PathVariable String id) {
	List<Person> people = personRepository.findByAddresses_Id(id);
    return people;
  }

  @Autowired
  private MongoTemplate mongoTemplate;

  private List<Person> findByStreetContaining(String street) {
    LookupOperation lookup = LookupOperation.newLookup().
        from("address").
        localField("addresses").
        foreignField("_id").
        as("addresses");

    MatchOperation match = Aggregation.match(Criteria.where("addresses.street").regex(".*"  + ".*"));

    ProjectionOperation project = Aggregation.project().andInclude("id", "name", "addresses");

    Aggregation aggregation = Aggregation.newAggregation(lookup, match, project);

    AggregationResults<Person> result = mongoTemplate.aggregate(aggregation, "person", Person.class);
    return result.getMappedResults();
  }
}
