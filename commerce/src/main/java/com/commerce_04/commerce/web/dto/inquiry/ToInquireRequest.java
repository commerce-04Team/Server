package com.commerce_04.commerce.web.dto.inquiry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToInquireRequest {

	private String userId;
	private Long productId;
	private String text;

}
