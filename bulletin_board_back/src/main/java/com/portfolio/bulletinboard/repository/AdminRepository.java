package com.portfolio.bulletinboard.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.bulletinboard.entity.AdminCategory;
import com.portfolio.bulletinboard.entity.Category;

@Mapper
public interface AdminRepository {

	public List<AdminCategory> getAdminCategories();
	public int addCategory(String newCategoryName);
	public List<Category> getCategories();
	public int deleteCategory(int categoryId);
}
