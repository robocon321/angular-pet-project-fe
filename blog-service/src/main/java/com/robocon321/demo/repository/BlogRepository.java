package com.robocon321.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.robocon321.demo.model.Blog;

public interface BlogRepository extends MongoRepository<Blog, String>{

}
