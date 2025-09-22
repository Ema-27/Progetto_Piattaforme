package com.educative.ecommerce.controllers;

import com.educative.ecommerce.model.Category;
import com.educative.ecommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<List<Category>> getCategories() {
		List<Category> body = categoryService.listCategories();
		return ResponseEntity.ok(body);
	}

	@PostMapping
	public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
		boolean exists = categoryService.readCategoryByName(category.getCategoryName()).isPresent();
		if (exists) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists");
		}
		Category created = categoryService.createCategory(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<?> updateCategory(@PathVariable("categoryId") Integer categoryId,
											@Valid @RequestBody Category category) {
		boolean exists = categoryService.readCategory(categoryId).isPresent();
		if (!exists) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category does not exist");
		}
		Category updated = categoryService.updateCategory(categoryId, category);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.noContent().build();
	}
}
