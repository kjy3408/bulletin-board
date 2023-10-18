package com.portfolio.bulletinboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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



	@Transactional
	public int deleteCategory(int categoryId) {
	    try {
	        adminRepository.deletePost(categoryId);
	    } catch (Exception e) {
	    	
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	        return 0; 
	    }

	    return adminRepository.deleteCategory(categoryId);
	}

}
