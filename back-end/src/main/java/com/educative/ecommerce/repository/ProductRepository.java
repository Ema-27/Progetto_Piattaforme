package com.educative.ecommerce.repository;

import com.educative.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByCode(String code);
    // Altri metodi custom qui se servono (es: ricerca per nome, per categoria, ecc)
}
