package com.commerce_04.commerce.Service.wishlist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WishlistErrorCode {

	PRODUCTS_ON_WISHLIST("이미 관심 목록에 존재하는 상품입니다.");


	private final String description;

}
