package com.robocon321.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;
import com.robocon321.demo.model.Message;
import com.robocon321.demo.model.QMessage;

public interface MessageRepository extends MongoRepository<Message, String>, QuerydslPredicateExecutor<Message>, QuerydslBinderCustomizer<QMessage> {
    @Override
    default public void customize(QuerydslBindings bindings, QMessage root) {
        bindings.bind(String.class).first(
          (StringPath path, String value) -> path.containsIgnoreCase(value));
    }	
}
