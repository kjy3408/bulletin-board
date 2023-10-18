package com.portfolio.bulletinboard.controller;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.bulletinboard.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	
	@GetMapping("/category")
	public ResponseEntity<?> getAdminCategories() {
		return ResponseEntity.ok().body(adminService.getAdminCategories());
	}
	
	@PostMapping("/add/category")
	public ResponseEntity<?> addCategory(@RequestBody Map<String, String> newCategoryName) {
		
		return ResponseEntity.ok().body(adminService.addCategory(newCategoryName));
	}
	
	@GetMapping("/categories")
	public ResponseEntity<?> getCategories() {
		return ResponseEntity.ok().body(adminService.getCategories());
	}
	
	@DeleteMapping("/delete/category")
	public ResponseEntity<?> deleteCategory(int categoryId) {
		return ResponseEntity.ok().body(adminService.deleteCategory(categoryId));
	}
}
