package com.educative.ecommerce.service;

import com.educative.ecommerce.model.Category;
import com.educative.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Transactional(readOnly = true)
	public List<Category> listCategories() {
		return categoryRepository.findAll();
	}

	@Transactional
	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Transactional(readOnly = true)
	public Optional<Category> readCategoryByName(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}

	@Transactional(readOnly = true)
	public Optional<Category> readCategory(Integer categoryId) {
		return categoryRepository.findById(categoryId);
	}

	@Transactional
	public Category updateCategory(Integer categoryId, Category newCategory) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Categoria non trovata: " + categoryId));
		category.setCategoryName(newCategory.getCategoryName());
		category.setDescription(newCategory.getDescription());
		category.setImageUrl(newCategory.getImageUrl());
		return categoryRepository.save(category);
	}

	@Transactional
	public void deleteCategory(Integer categoryId) {
		if(!categoryRepository.existsById(categoryId)){
			throw new RuntimeException("Categoria non trovata: " + categoryId);
		}
		categoryRepository.deleteById(categoryId);
	}
}
