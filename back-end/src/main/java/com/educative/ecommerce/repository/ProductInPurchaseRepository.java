package com.educative.ecommerce.repository;

import com.educative.ecommerce.model.ProductInPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInPurchaseRepository extends JpaRepository<ProductInPurchase, Integer> {
}
