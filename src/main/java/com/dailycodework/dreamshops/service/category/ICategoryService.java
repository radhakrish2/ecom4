package com.dailycodework.dreamshops.service.category;

import java.util.List;

import com.dailycodework.dreamshops.entity.Category;

public interface ICategoryService {
	Category getCategoryById(int id);
	Category getCategoryByName(String name);
	List<Category> getAllCategories();
	Category addCategory(Category category);
	Category updateCategory(Category category,int id);
	void deleteCategory(int id);

}
