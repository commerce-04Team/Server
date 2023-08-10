package com.commerce_04.commerce.Service.inquiry.exception;

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
public class InquiryErrorResponse {

	private InquiryErrorCode errorCode;
	private String errorMessage;
}
