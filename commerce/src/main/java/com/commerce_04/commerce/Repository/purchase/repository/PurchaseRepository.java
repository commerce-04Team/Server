package com.commerce_04.commerce.Repository.purchase.repository;

import com.commerce_04.commerce.Repository.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    Purchase findByUserIdAndProductId(String userId, Long productId);

    @Query(
            "SELECT new com.commerce_04.commerce.Repository.purchase.repository." +
                    "MyPurchase(pc.id, p.id, payer.id, p.title, p.price, pc.purchaseAt) " +
                    "FROM Purchase pc " +
                    "JOIN pc.product p " +
                    "JOIN p.store s " +
                    "JOIN s.user seller " +
                    "JOIN pc.user payer " +
                    "WHERE seller.id = :userId"
    )
    List<MyPurchase> findMyPurchaseList(String userId);
}
