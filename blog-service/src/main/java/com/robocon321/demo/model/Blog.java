package com.robocon321.demo.model;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("blog")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Blog {
	@Id
	private String id;

	@NotBlank
	private String title;
	
	@NotBlank
	private String image;
	
	@NotBlank
	private String description;
	
	@CreatedDate
	private LocalDate createDate;
	
	@CreatedBy
	private String userId;
}
