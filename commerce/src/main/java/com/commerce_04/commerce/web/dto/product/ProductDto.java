package com.commerce_04.commerce.web.dto.product;

import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.Repository.product.entity.ProductImage;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
@Builder
public class ProductDto {

    private String userId;
    private Long productId;
    private String category;
    private Long storeId;
    private List<String> productImages=new ArrayList<>();
    private String title;
    private Integer price;
    private String contents;
    private String productStatus;
    private String createAt;
    private String updateAt;
    private String deleteAt;
    private boolean isDelete;
    private Long wishCount;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static ProductDto toResponse(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .userId(product.getStore().getUser().getId())
                .category(product.getCategory().getCategory().name())
                .storeId(product.getStore().getId())
                .productImages(product.getProductImages().stream().map(ProductImage::getImageUrl).collect(Collectors.toList()))
                .title(product.getTitle())
                .price(product.getPrice())
                .contents(product.getContents())
                .productStatus(product.getProductStatus())
                .createAt(product.getCreateAt().format(formatter))
                .updateAt(product.getUpdateAt().format(formatter))
                .deleteAt(product.getDeleteAt().format(formatter))
                .isDelete(product.getIsDelete())
                .wishCount(product.getWishCount())
                .build();
    }

    public static List<ProductDto> toResponse(List<Product> list) {
        return list.stream().map(ProductDto::toResponse).collect(Collectors.toList());
    }
}
