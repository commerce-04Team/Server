package com.commerce_04.commerce.web.dto.product;

import com.commerce_04.commerce.Repository.product.entity.ProductImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class UpdateProduct {

    private String userId;
    private Long productId;
    private String category;
    private Long storeId;
    private List<ProductImageDto> productImages;
    private String title;
    private Integer price;
    private String contents;
    private String productStatus;
    private String createAt;
    private String updateAt;
    private String deleteAt;
    private boolean isDelete;


}
