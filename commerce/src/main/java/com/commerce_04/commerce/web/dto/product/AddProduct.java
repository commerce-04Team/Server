package com.commerce_04.commerce.web.dto.product;

import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.Repository.product.entity.ProductImage;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class AddProduct {

    private String userId;
    private Long productId;
    private String category;
    private Long storeId;
    private List<String> productImages;
    private String title;
    private Integer price;
    private String contents;
    private String productStatus;
    private String createAt;
    private String updateAt;
    private String deleteAt;
    private boolean isDelete;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

}
