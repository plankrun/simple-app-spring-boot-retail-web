package com.example.retailapplication.repository;

import com.example.retailapplication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class to access Product Entity on database
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Find product by productName
     * @param productName to be found
     * @return Product entity
     */
    Product findByProductName(String productName);
}
