package com.commerce_04.commerce.web.controller.purchase;

import com.commerce_04.commerce.Service.purchase.PurchaseService;
import com.commerce_04.commerce.web.dto.purchase.AddPurchaseRequest;
import com.commerce_04.commerce.web.dto.purchase.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/purchases")
@RequiredArgsConstructor
@Slf4j
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<?> completePurchase(@RequestBody AddPurchaseRequest addPurchaseRequest) {
        purchaseService.addPurchase(addPurchaseRequest);

        return ResponseEntity.ok("구매 완료되었습니다!");
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> getPurchases(@RequestParam String userId) {
        return ResponseEntity.ok(purchaseService.getPurchases(userId));
    }

    @GetMapping("/detail")
    public PurchaseResponse getPurchase(@RequestParam Long productId,@RequestParam String userId) {
        return purchaseService.getPurchase(productId,userId);
    }


}
