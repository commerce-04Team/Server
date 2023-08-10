package com.commerce_04.commerce.web.dto.inquiry;

import com.commerce_04.commerce.Repository.inquiry.repository.InquiriesReceived;
import java.util.List;
import java.util.stream.Collectors;
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
public class InquiriesReceivedResponse {

	private Long inquiryId;
	private String sellerId;
	private String registeredUserId;
	private Long productId;
	private Long storeId;
	private String inquiryStatus;

	public static InquiriesReceivedResponse toResponse(
		InquiriesReceived inquiriesReceived) {
		return InquiriesReceivedResponse.builder()
			.inquiryId(inquiriesReceived.getInquiryId())
			.sellerId(inquiriesReceived.getSellerId())
			.registeredUserId(inquiriesReceived.getRegisteredUserId())
			.productId(inquiriesReceived.getProductId())
			.storeId(inquiriesReceived.getStoreId())
			.inquiryStatus(
				inquiriesReceived.getInquiryStatus().getInquiryStatus())
			.build();
	}

	public static List<InquiriesReceivedResponse> toResponse(
		List<InquiriesReceived> list) {
		return list.stream().map(InquiriesReceivedResponse::toResponse).collect(
			Collectors.toList());
	}
}
