package com.commerce_04.commerce.Repository.product.repository;

import com.commerce_04.commerce.Repository.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}