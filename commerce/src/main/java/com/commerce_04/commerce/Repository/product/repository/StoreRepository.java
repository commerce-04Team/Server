package com.commerce_04.commerce.Repository.product.repository;

import com.commerce_04.commerce.Repository.product.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByUserId(String userId);
}
