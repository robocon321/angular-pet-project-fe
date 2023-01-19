package com.robocon321.demo.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlogRequest {
	@NotBlank(message = "Title must not be blank")
	private String title;
	@NotBlank(message = "Image must not be null")
	private MultipartFile image;
	@NotBlank(message = "Description must not be null")
	private String description;
}
