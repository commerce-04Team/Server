package com.commerce_04.commerce.Repository.wishlist.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyWishlist {

	private Long wishlistId;
	private Long productId;
	private Long storeId;
	private String sellerId;
	private String title;
	private Integer price;
	private String productStatus;
}
