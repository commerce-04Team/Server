package com.commerce_04.commerce.web.controller.product;

import com.commerce_04.commerce.Service.product.ProductService;
import com.commerce_04.commerce.web.dto.product.AddProduct;
import com.commerce_04.commerce.web.dto.product.ProductRequest;
import com.commerce_04.commerce.web.dto.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ProductResponse findAllProducts() {
        List<ProductRequest> products = productService.findAllProducts();
        return new ProductResponse(products);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody AddProduct addProduct) {
        productService.addProduct(addProduct);
        return ResponseEntity.ok("상품이 등록되었습니다!");
    }

    @GetMapping("/")
    public ProductRequest getProduct(@RequestParam Long productId) {
        return productService.getProduct(productId);

    }


}
