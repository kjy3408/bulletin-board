package com.portfolio.bulletinboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.portfolio.bulletinboard.entity.AdminCategory;
import com.portfolio.bulletinboard.entity.Category;
import com.portfolio.bulletinboard.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminRepository adminRepository;
	
	public List<AdminCategory> getAdminCategories() {
		return adminRepository.getAdminCategories();
	}
	
	public int addCategory(Map<String, String> newCategoryName) {
		
		return adminRepository.addCategory(newCategoryName.get("newCategoryName"));
	}
	
	public List<Category> getCategories() {
		System.out.println(adminRepository.getCategories());
		return adminRepository.getCategories();
	}

	public int deleteCategory(int categoryId) {
		
		return adminRepository.deleteCategory(categoryId);
	}
}
