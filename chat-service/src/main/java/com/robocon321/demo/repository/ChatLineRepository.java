package com.robocon321.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;
import com.robocon321.demo.model.ChatLine;
import com.robocon321.demo.model.QChatLine;
import com.robocon321.demo.model.Room;

public interface ChatLineRepository extends MongoRepository<ChatLine, String>, QuerydslPredicateExecutor<ChatLine>, QuerydslBinderCustomizer<QChatLine> {
    @Override
    default public void customize(QuerydslBindings bindings, QChatLine root) {
        bindings.bind(String.class).first(
          (StringPath path, String value) -> path.containsIgnoreCase(value));
    }	
    
    public List<ChatLine> findByRoom(Room room);
}
