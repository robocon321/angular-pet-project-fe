package com.robocon321.demo.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.Predicate;
import com.robocon321.demo.dto.request.BlogRequest;
import com.robocon321.demo.dto.response.BlogResponse;
import com.robocon321.demo.exception.CannotSaveImageException;
import com.robocon321.demo.model.Blog;
import com.robocon321.demo.model.User;
import com.robocon321.demo.repository.BlogRepository;
import com.robocon321.demo.repository.UserRepository;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	private RestTemplate restTemplate = new RestTemplate();
	
	public Page<BlogResponse> getPage(Pageable pageable, Predicate predicate) {
		Page<Blog> blogs = blogRepository.findAll(predicate, pageable);
		Page<BlogResponse> response = pageDocumentToDTO(blogs);
		return response;
	}
	
	private Page<BlogResponse> pageDocumentToDTO(Page<Blog> page) {
		return page.map(blog -> documentToDTO(blog));
	}
	
	private BlogResponse documentToDTO(Blog blog) {
		BlogResponse dto = new BlogResponse();
		BeanUtils.copyProperties(blog, dto);
		Optional<User> userOpt = userRepository.findById(blog.getUserId());
		if(userOpt.isPresent()) dto.setCreateBy(userOpt.get().getEmail()); 
		return dto;
	}

	public BlogResponse save(BlogRequest blogRequest) throws IOException {
		String path = storeToFileService(blogRequest.getImage());
//		String path = "/image/haha.txt";
		Blog blog = buildDocument(blogRequest.getTitle(), path, blogRequest.getDescription());
		BlogResponse response = storeBlog(blog);
		return response;
	}

	private BlogResponse storeBlog(Blog blog) {
		BlogResponse response = new BlogResponse();
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		blog.setUserId(user.getId());
		Blog newBlog = blogRepository.save(blog);
		BeanUtils.copyProperties(newBlog, response);
		response.setCreateBy(user.getId());
		return response;
	}

	private Blog buildDocument(String title, String path, String description) {
		Blog blog = Blog.builder().title(title).description(description).image(path).build();

		return blog;
	}

	private String storeToFileService(MultipartFile multipart) throws IOException {
		String uploadDir = "/images/blogs";
		String fileName = System.currentTimeMillis() + "";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
		body.add("fileName", fileName);
		body.add("uploadDir", uploadDir);
		body.add("file", multipart.getResource());

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(body,
				headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8001/api/file",
				request, String.class);
		if (responseEntity.getStatusCodeValue() < 200 && responseEntity.getStatusCodeValue() > 299)
			throw new CannotSaveImageException(responseEntity.getBody());
		String path = uploadDir + "/" + fileName + "." + getExtension(multipart);
		return path;
	}

	private String getExtension(MultipartFile multipart) {
		String[] multipartArr = multipart.getOriginalFilename().split("\\.");
		if (multipartArr.length == 0)
			throw new RuntimeException("File invalid");
		return multipartArr[multipartArr.length - 1];
	}

	public void delete(String id) {
		Optional<Blog> blogOpt = blogRepository.findById(id);
		if(blogOpt.isEmpty()) return ;
		blogRepository.deleteById(id);
		String image = blogOpt.get().getImage();
//		restTemplate.delete("http://localhost:8001/api/file", image);
		HttpEntity<String> request = new HttpEntity<String>(image);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8001/api/file", HttpMethod.DELETE, request, String.class);

	}

}
