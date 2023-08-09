package com.commerce_04.commerce.Repository.wishlist.repository;

import com.commerce_04.commerce.Repository.wishlist.entity.Wishlist;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

	boolean existsByUserIdAndProductId(String userId, Long productId);

	Optional<Wishlist> findByUserIdAndProductId(String userId, Long productId);
}
