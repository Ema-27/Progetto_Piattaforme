package com.educative.ecommerce.controllers;

import com.educative.ecommerce.dto.product.ProductDto;
import com.educative.ecommerce.model.Category;
import com.educative.ecommerce.service.CategoryService;
import com.educative.ecommerce.service.ProductService;
import com.educative.ecommerce.support.ProductAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductDto productDto) {
        Category category = categoryService.readCategory(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria non trovata: " + productDto.getCategoryId()));
        try {
            ProductDto saved = productService.addProduct(productDto, category);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (ProductAlreadyExistsException e) {
            return new ResponseEntity<>("Product already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productDtos = productService.listProducts();
        return ResponseEntity.ok(productDtos);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody @Valid ProductDto productDto) {
        Category category = categoryService.readCategory(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria non trovata: " + productDto.getCategoryId()));
        ProductDto updated = productService.updateProduct(productId, productDto, category);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
