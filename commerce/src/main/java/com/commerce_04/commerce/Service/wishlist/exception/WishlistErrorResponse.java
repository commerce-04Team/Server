package com.commerce_04.commerce.Service.wishlist.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistErrorResponse {

	private WishlistErrorCode errorCode;
	private String errorMessage;
}
