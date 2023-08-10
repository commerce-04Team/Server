package com.commerce_04.commerce.web.dto.purchase;

import com.commerce_04.commerce.Repository.purchase.entity.Purchase;
import com.commerce_04.commerce.Repository.purchase.repository.MyPurchase;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class PurchaseResponse {

    private Long purchaseId;
    private Long productId;
    private String payerId;
    private String title;
    private Integer price;
    private LocalDateTime purcasesAt;

    public static PurchaseResponse toResponse(Purchase purchase) {
        return PurchaseResponse.builder()
                .purchaseId(purchase.getId())
                .productId(purchase.getProduct().getId())
                .payerId(purchase.getUser().getId())
                .title(purchase.getProduct().getTitle())
                .price(purchase.getProduct().getPrice())
                .purcasesAt(purchase.getPurchaseAt())
                .build();
    }

    private static PurchaseResponse toResponse(MyPurchase myPurchase) {
        return PurchaseResponse.builder()
                .purchaseId(myPurchase.getPurchaseId())
                .productId(myPurchase.getProductId())
                .payerId(myPurchase.getPayerId())
                .title(myPurchase.getTitle())
                .price(myPurchase.getPrice())
                .purcasesAt(myPurchase.getPurchaseAt())
                .build();
    }

    public static List<PurchaseResponse> toResponse(List<MyPurchase> list) {
        return list.stream().map(PurchaseResponse::toResponse).collect(Collectors.toList());
    }


}
