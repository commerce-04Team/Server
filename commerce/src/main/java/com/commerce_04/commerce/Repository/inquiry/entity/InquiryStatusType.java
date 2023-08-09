package com.commerce_04.commerce.Repository.inquiry.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InquiryStatusType {
	READ("읽음"),
	DO_NOT_READ("읽지 않음"),
	ANSWER_COMPLETE("답변 완료");

	private final String inquiryStatus;
}
