package com.commerce_04.commerce.Repository.product.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CategoryType {
	CLOTHING("의류"),
	LAPTOP("노트북"),
	PHONE("휴대폰")
	;

	private final String category;
}
