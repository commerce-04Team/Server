package com.commerce_04.commerce.Repository.inquiry.entity;

import static com.commerce_04.commerce.Service.inquiry.exception.InquiryErrorCode.NO_INQUIRY_STATUS_MATCHES;

import com.commerce_04.commerce.Service.inquiry.exception.InquiryException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InquiryStatusType {
	READ("읽음"),
	DO_NOT_READ("읽지 않음"),
	ANSWER_COMPLETE("답변 완료");

	private final String inquiryStatus;

	public static InquiryStatusType findByInquiryStatus(String input) {
		return Arrays.stream(InquiryStatusType.values())
			.filter(type -> type.getInquiryStatus().equals(input))
			.findAny()
			.orElseThrow(() -> new InquiryException(NO_INQUIRY_STATUS_MATCHES));
	}
}
