package com.commerce_04.commerce.Service.wishlist.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistException extends RuntimeException {

	private WishlistErrorCode errorCode;
	private String errorMessage;

	public WishlistException(WishlistErrorCode errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}
}
