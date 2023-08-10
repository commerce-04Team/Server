package com.commerce_04.commerce.web.controller.purchase;

import com.commerce_04.commerce.Service.purchase.PurchaseService;
import com.commerce_04.commerce.web.dto.purchase.AddPurchaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/purchase")
@RequiredArgsConstructor
@Slf4j
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<?> completePurchase(@RequestBody AddPurchaseRequest addPurchaseRequest) {
        log.info("AddPurchaseRequest {}",addPurchaseRequest.getProductId());
        log.info("AddPurchaseRequest {}",addPurchaseRequest.getUserId());
        purchaseService.addPurchase(addPurchaseRequest);

        return ResponseEntity.ok("구매 완료되었습니다!");
    }
}
