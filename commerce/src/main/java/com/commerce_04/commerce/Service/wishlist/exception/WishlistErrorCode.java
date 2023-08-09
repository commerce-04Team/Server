package com.commerce_04.commerce.Service.wishlist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WishlistErrorCode {

	PRODUCTS_ON_THE_WISHLIST("이미 관심 목록에 존재하는 상품입니다."),
	PRODUCTS_IS_NOT_ON_THE_WISHLIST("상품이 관심 목록에 없습니다."),
	WISHLIST_ID_DOES_NOT_EXIST("WISHLIST_ID가 존재하지 않습니다.")
	;



	private final String description;

}
