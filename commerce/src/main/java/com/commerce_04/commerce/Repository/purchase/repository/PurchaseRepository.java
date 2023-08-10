package com.commerce_04.commerce.Repository.purchase.repository;

import com.commerce_04.commerce.Repository.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
}
