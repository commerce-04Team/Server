package com.commerce_04.commerce.Repository.inquiry.repository;

import com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredInquiry {

	private Long inquiryId;
	private String sellerId;
	private String registeredUserId;
	private Long productId;
	private Long storeId;
	private InquiryStatusType inquiryStatus;
}
