package com.commerce_04.commerce.web.dto.purchase;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddPurchaseRequest {

    private String userId;
    private Long productId;
}
