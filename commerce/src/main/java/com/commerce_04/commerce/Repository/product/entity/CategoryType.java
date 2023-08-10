package com.commerce_04.commerce.Repository.product.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CategoryType {
	CLOTHING("의류"),
	LAPTOP("노트북"),
	PHONE("휴대폰")
	;

	private final String category;

	public static CategoryType findByCategoryType(String input) {
		return Arrays.stream(CategoryType.values())
				.filter(type ->type.getCategory().equals(input))
				.findAny()
				.orElseThrow(() ->new RuntimeException("존재하지 않는 카테고리 입니다."));
	}
}
