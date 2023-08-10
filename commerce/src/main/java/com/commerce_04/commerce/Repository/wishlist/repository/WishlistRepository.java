package com.commerce_04.commerce.Repository.wishlist.repository;

import com.commerce_04.commerce.Repository.wishlist.entity.Wishlist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

	boolean existsByUserIdAndProductId(String userId, Long productId);

	Optional<Wishlist> findByUserIdAndProductId(String userId, Long productId);

	@Query(
		" SELECT new com.commerce_04.commerce.Repository.wishlist.repository." +
			"MyWishlist(w.id, p.id, s.id, seller.id, p.title, p.price, p.productStatus) " +
			"FROM Wishlist w " +
			"JOIN w.product p " +
			"JOIN p.store s " +
			"JOIN s.user seller " +
			"JOIN w.user payer " +
			"WHERE w.user.id = :userId")
	List<MyWishlist> findMyWishlist(String userId);
}
