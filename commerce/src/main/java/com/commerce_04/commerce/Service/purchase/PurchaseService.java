package com.commerce_04.commerce.Service.purchase;

import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.Repository.product.repository.ProductRepository;
import com.commerce_04.commerce.Repository.purchase.entity.Purchase;
import com.commerce_04.commerce.Repository.purchase.repository.PurchaseRepository;
import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Repository.user.Entity.UserRepository;
import com.commerce_04.commerce.web.dto.purchase.AddPurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addPurchase(AddPurchaseRequest addPurchaseRequest) {
        String userId=addPurchaseRequest.getUserId();
        Long productId= addPurchaseRequest.getProductId();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));

        product.setProductStatus("C");
        purchaseRepository.save(Purchase.toEntity(user,product));
    }
}
