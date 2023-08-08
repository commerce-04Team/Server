package com.commerce_04.commerce.Repository.wishlist.repository;

import com.commerce_04.commerce.Repository.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

}
