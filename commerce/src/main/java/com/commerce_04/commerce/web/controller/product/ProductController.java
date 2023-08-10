package com.commerce_04.commerce.web.controller.product;

import com.commerce_04.commerce.Service.product.ProductService;
import com.commerce_04.commerce.web.dto.product.AddProduct;
import com.commerce_04.commerce.web.dto.product.ProductDto;
import com.commerce_04.commerce.web.dto.product.ProductResponse;
import com.commerce_04.commerce.web.dto.product.UpdateProduct;
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
        List<ProductDto> products = productService.findAllProducts();
        return new ProductResponse(products);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody AddProduct addProduct) {
        productService.addProduct(addProduct);
        return ResponseEntity.ok("상품이 등록되었습니다!");
    }

    @GetMapping("/")
    public ProductDto getProduct(@RequestParam Long productId) {
        return productService.getProduct(productId);

    }

    @GetMapping("/store")
    public ProductResponse getStoreProducts(@RequestParam Long storeId) {
        List<ProductDto> storeProducts = productService.getStoreProducts(storeId);
        return new ProductResponse(storeProducts);
    }

    //    @PutMapping
//    public ProductDto updateProduct(@RequestBody UpdateProduct product) {
//        return productService.updateProduct(product);
//    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProduct product) {
        productService.updateProduct(product);
        return ResponseEntity.ok("상품이 수정되었습니다!");
    }

}
