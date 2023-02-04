package com.robocon321.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;
import com.robocon321.demo.model.QRoom;
import com.robocon321.demo.model.Room;
import com.robocon321.demo.model.User;

public interface RoomRepository extends MongoRepository<Room, String>, QuerydslPredicateExecutor<Room>, QuerydslBinderCustomizer<QRoom> {
    @Override
    default public void customize(QuerydslBindings bindings, QRoom root) {
        bindings.bind(String.class).first(
          (StringPath path, String value) -> path.containsIgnoreCase(value));
    }
	
    List<Room> findByMembers(User user);
}
