package com.educative.ecommerce.service;

import com.educative.ecommerce.dto.product.ProductDto;
import com.educative.ecommerce.model.Category;
import com.educative.ecommerce.model.Product;
import com.educative.ecommerce.repository.ProductRepository;
import com.educative.ecommerce.support.ProductAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Mapper DTO → Entity (statico, senza setId!)
    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl()); // Corretto: imageUrl
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setCode(productDto.getCode());
        return product;
    }

    @Transactional
    public ProductDto addProduct(ProductDto productDto, Category category) throws ProductAlreadyExistsException {
        if (productRepository.existsByCode(productDto.getCode()))
            throw new ProductAlreadyExistsException();

        Product product = getProductFromDto(productDto, category);
        Product savedProduct = productRepository.save(product);
        return new ProductDto(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> listProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto updateProduct(Integer productId, ProductDto productDto, Category category) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato: " + productId));
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setCode(productDto.getCode());

        Product updatedProduct = productRepository.save(product);
        return new ProductDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Prodotto non trovato: " + id);
        }
        productRepository.deleteById(id);
    }
}
