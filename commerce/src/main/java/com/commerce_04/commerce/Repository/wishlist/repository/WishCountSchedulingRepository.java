package com.commerce_04.commerce.Repository.wishlist.repository;

import com.commerce_04.commerce.Repository.wishlist.entity.WishCountScheduling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishCountSchedulingRepository extends
	JpaRepository<WishCountScheduling, Long> {

	boolean existsByProductId(Long productId);
}
