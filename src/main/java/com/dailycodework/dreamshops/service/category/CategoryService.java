package com.dailycodework.dreamshops.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dailycodework.dreamshops.entity.Category;
import com.dailycodework.dreamshops.exception.AlreadyExistsException;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
	
	private final CategoryRepository categoryRepository;
	@Override
	public Category getCategoryById(int id) {
		
		return categoryRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Category Not Found!!!"));
	}

	@Override
	public Category getCategoryByName(String name) {
		
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
	
		return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
				.map(categoryRepository::save).orElseThrow(()-> new AlreadyExistsException(category.getName()+" already Exists"));
	}

	@Override
	public Category updateCategory(Category category,int  id) {
		
		return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
			oldCategory.setName(category.getName());
			return categoryRepository.save(oldCategory);
		}).orElseThrow(()-> new ResourceNotFoundException("Category Not Found!!!"));
	}

	@Override
	public void deleteCategory(int id) {
		categoryRepository.findById(id)
		     .ifPresentOrElse(categoryRepository::delete, ()->{
		    	 throw new ResourceNotFoundException("Category ID Not Found!!!");
		     });
		
	}
	
	
	

}
