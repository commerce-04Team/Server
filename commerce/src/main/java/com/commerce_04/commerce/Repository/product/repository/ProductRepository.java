package com.commerce_04.commerce.Repository.product.repository;

import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.web.dto.product.ProductRequest;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


}