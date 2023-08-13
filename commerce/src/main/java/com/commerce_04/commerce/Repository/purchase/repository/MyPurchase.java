package com.commerce_04.commerce.Repository.purchase.repository;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPurchase {

    private Long purchaseId;
    private Long productId;
    private String payerId;
    private String title;
    private Integer price;
    private LocalDateTime purchaseAt;
}
