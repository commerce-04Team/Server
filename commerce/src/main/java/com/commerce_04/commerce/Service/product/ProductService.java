package com.commerce_04.commerce.Service.product;

import com.commerce_04.commerce.Repository.product.entity.*;
import com.commerce_04.commerce.Repository.product.repository.CategoryRepository;
import com.commerce_04.commerce.Repository.product.repository.ProductImageRepository;
import com.commerce_04.commerce.Repository.product.repository.ProductRepository;
import com.commerce_04.commerce.Repository.product.repository.StoreRepository;
import com.commerce_04.commerce.Repository.user.Entity.UserRepository;
import com.commerce_04.commerce.web.dto.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void updateProduct(UpdateProduct product) {
        Store store = storeRepository.findByUserId(product.getUserId()).orElseThrow(()->new RuntimeException("상점이 존재하지 않습니다."));
        Category category = categoryRepository.findByCategory(CategoryType.findByCategoryType(product.getCategory())).orElseThrow(()->new RuntimeException("카테고리가 존재하지 않습니다."));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Product updateProduct=Product.builder()
                .id(product.getProductId())
                .store(store)
                .category(category)
                .title(product.getTitle())
                .price(product.getPrice())
                .contents(product.getContents())
                .productStatus(product.getProductStatus())
                .createAt(LocalDateTime.parse(product.getCreateAt(),formatter))
                .updateAt(LocalDateTime.parse(product.getUpdateAt(),formatter))
                .deleteAt(LocalDateTime.parse(product.getDeleteAt(),formatter))
                .isDelete(product.isDelete())
                .build();
        productRepository.save(updateProduct);

        List<ProductImage> updateProdImg = productImageRepository.findByProductId(product.getProductId());
        List<ProductImageDto> updateProdImgDto = product.getProductImages();

        for(ProductImage img:updateProdImg) {
            for(ProductImageDto imgDto:updateProdImgDto) {
                if(imgDto.getImageId().equals(img.getId()))
                    img.setImageUrl(imgDto.getImageUrl());
            }

        }
        //return getProduct(product.getProductId());
    }


    @Transactional
    public void deleteProduct(Long productId) {
        Product productEntity = productRepository.findById(productId).orElseThrow(()->new RuntimeException("존재하지 않는 상품입니다."));
        productEntity.setIsDelete(true);
    }
}
