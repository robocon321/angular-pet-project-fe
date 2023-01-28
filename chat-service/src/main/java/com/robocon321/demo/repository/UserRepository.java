package com.robocon321.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;
import com.robocon321.demo.model.QUser;
import com.robocon321.demo.model.User;

public interface UserRepository extends MongoRepository<User, String>, QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser>{
    @Override
    default public void customize(QuerydslBindings bindings, QUser root) {
        bindings.bind(String.class).first(
          (StringPath path, String value) -> path.containsIgnoreCase(value));
    }

    public Optional<User> findByEmail(String email);
}
