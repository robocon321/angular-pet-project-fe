package com.robocon321.demo.model;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_line")
public class ChatLine {
	@Id
	private String id;
	@CreatedBy
	@DocumentReference
	private User createBy;
	@CreatedDate
	private LocalDate createDate;
	@DocumentReference
	private Room room;
	@DocumentReference
	private ChatLine replyTo;
	@DocumentReference
	private Message message;
}
