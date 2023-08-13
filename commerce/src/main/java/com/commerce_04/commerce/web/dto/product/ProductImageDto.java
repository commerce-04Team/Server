package com.commerce_04.commerce.web.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductImageDto {
    private Long imageId;
    private String imageUrl;
}
