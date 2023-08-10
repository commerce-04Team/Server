package com.commerce_04.commerce.Service.inquiry.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InquiryErrorCode {

	NO_INQUIRY_STATUS_MATCHES("존재하지 않는 문의 상태입니다."),
	NO_INQUIRY_MATCHES("존재하지 않는 문의입니다."),
	YOU_DO_NOT_HAVE_PERMISSION("권한이 없습니다.")
	;



	private final String description;

}
