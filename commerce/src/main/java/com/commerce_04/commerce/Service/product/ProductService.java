package com.commerce_04.commerce.Service.product;

import com.commerce_04.commerce.Repository.product.entity.*;
import com.commerce_04.commerce.Repository.product.repository.CategoryRepository;
import com.commerce_04.commerce.Repository.product.repository.ProductImageRepository;
import com.commerce_04.commerce.Repository.product.repository.ProductRepository;
import com.commerce_04.commerce.Repository.product.repository.StoreRepository;
import com.commerce_04.commerce.Repository.user.Entity.UserRepository;
import com.commerce_04.commerce.web.dto.product.AddProduct;
import com.commerce_04.commerce.web.dto.product.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;


    public List<ProductDto> findAllProducts() {
        List<Product> productEntities = productRepository.findAll();
        return ProductDto.toResponse(productEntities);
    }

    public void addProduct(AddProduct addProduct) {

        Store store = storeRepository.findByUserId(addProduct.getUserId()).orElseThrow(()->new RuntimeException("상점이 존재하지 않습니다."));
        Category category = categoryRepository.findByCategory(CategoryType.findByCategoryType(addProduct.getCategory())).orElseThrow(()->new RuntimeException("카테고리가 존재하지 않습니다."));

        Product product=Product.toEntity(addProduct, store, category);
        productRepository.save(product);
        addProduct.getProductImages().forEach(image->{
            productImageRepository.save(ProductImage.toEntity(image,product));
        });
    }

    public ProductDto getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));
        return ProductDto.toResponse(product);
    }

    public List<ProductDto> getStoreProducts(Long id) {
        List<Product> products = productRepository.findByStoreId(id);
        return ProductDto.toResponse(products);
    }
}
