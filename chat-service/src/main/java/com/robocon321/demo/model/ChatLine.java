package com.robocon321.demo.model;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
	private String createBy;
	@CreatedDate
	private LocalDate createDate;
	private Room room;
	private ChatLine replyTo;
}
