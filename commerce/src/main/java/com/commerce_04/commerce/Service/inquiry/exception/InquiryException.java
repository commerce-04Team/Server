package com.commerce_04.commerce.Service.inquiry.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryException extends RuntimeException {

	private InquiryErrorCode errorCode;
	private String errorMessage;

	public InquiryException(InquiryErrorCode errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}
}
