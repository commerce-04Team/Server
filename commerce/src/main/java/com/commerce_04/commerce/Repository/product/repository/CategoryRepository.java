package com.commerce_04.commerce.Repository.product.repository;

import com.commerce_04.commerce.Repository.product.entity.Category;
import com.commerce_04.commerce.Repository.product.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByCategory(CategoryType categoryType);

}
