package com.portfolio.bulletinboard.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.bulletinboard.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	
	@GetMapping("/categories")
	public ResponseEntity<?> getCategoryies() {
		return ResponseEntity.ok().body(postService.getCategories());
	}
	
	@PostMapping("/post")
	public ResponseEntity<?> registerPost(@RequestParam("title") String title,
	                                       @RequestParam("content") String content,
	                                       @RequestParam("categoryId") int categoryId,
	                                       @RequestParam("file") List<MultipartFile> files) {
	
		return ResponseEntity.ok().body(postService.registerPost(title, content, categoryId, files));
	}
	
	@GetMapping("/posts")
	public ResponseEntity<?> getPosts(int page, String searchValue, int categoryId) {
		return ResponseEntity.ok().body(postService.getPosts(page, searchValue, categoryId));
	}
	
	@GetMapping("/read/{postId}")
	public ResponseEntity<?> readPostData(@PathVariable int postId) {
		return ResponseEntity.ok().body(postService.readPostData(postId));
	}
	
}
