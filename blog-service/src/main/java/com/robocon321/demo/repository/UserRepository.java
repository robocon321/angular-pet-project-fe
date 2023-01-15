package com.robocon321.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.robocon321.demo.model.Blog;
import com.robocon321.demo.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByEmail(String email);
	
	// @Update("{ '$push' : { 'Blog' : ?1 } }")
	Optional<Blog> findAndPushBlogById(String id, Blog blog);
}
