package com.commerce_04.commerce.Repository.product.repository;

import com.commerce_04.commerce.Repository.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStoreId(long id);


}