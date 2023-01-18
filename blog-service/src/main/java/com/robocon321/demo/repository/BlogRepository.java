package com.robocon321.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;
import com.robocon321.demo.model.Blog;
import com.robocon321.demo.model.QBlog;

public interface BlogRepository extends MongoRepository<Blog, String>, QuerydslPredicateExecutor<Blog>, QuerydslBinderCustomizer<QBlog>{
    @Override
    default public void customize(QuerydslBindings bindings, QBlog root) {
        bindings.bind(String.class).first(
          (StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
