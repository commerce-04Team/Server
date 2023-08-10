package com.commerce_04.commerce.web.dto.inquiry;

import com.commerce_04.commerce.Repository.inquiry.entity.Inquiry;
import com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatus;
import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.Repository.product.entity.Store;
import com.commerce_04.commerce.Repository.user.Entity.User;
import java.time.LocalDateTime;
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
public class GetInquiryDetailResponse {

	private Long inquiryId;
	private String userId;
	private Long productId;
	private Long storeId;
	private String inquiryStatus;
	private String text;
	private String answer;
	private LocalDateTime createAt;
	private LocalDateTime answerAt;

	public static GetInquiryDetailResponse toResponse(Inquiry inquiry) {
		User user = inquiry.getUser();
		Product product = inquiry.getProduct();
		Store store = product.getStore();
		InquiryStatus inquiryStatus = inquiry.getInquiryStatus();

		return GetInquiryDetailResponse.builder()
			.inquiryId(inquiry.getId())
			.userId(user.getId())
			.productId(product.getId())
			.storeId(store.getId())
			.inquiryStatus(inquiryStatus.getInquiryStatus().getInquiryStatus())
			.text(inquiry.getText())
			.answer(inquiry.getAnswer())
			.createAt(inquiry.getCreateAt())
			.answerAt(inquiry.getAnswerAt())
			.build();
	}
}
