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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("seen")
public class Seen {
	@Id
	private String id;
	@CreatedDate
	private LocalDate createDate;
	@CreatedBy
	private User createBy;
	private ChatLine chatLine;
}
