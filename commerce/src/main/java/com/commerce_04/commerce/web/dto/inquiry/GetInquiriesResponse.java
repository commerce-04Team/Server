package com.commerce_04.commerce.web.dto.inquiry;

import com.commerce_04.commerce.Repository.inquiry.entity.Inquiry;
import com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatus;
import com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatusType;
import com.commerce_04.commerce.Repository.inquiry.repository.RegisteredInquiry;
import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.Repository.product.entity.Store;
import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Repository.wishlist.repository.MyWishlist;
import com.commerce_04.commerce.web.dto.wishlist.WishlistResponse;
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
public class GetInquiriesResponse {

	private Long inquiryId;
	private String sellerId;
	private String registeredUserId;
	private Long productId;
	private Long storeId;
	private String inquiryStatus;

	public static GetInquiriesResponse toResponse(Inquiry inquiry) {
		Product product = inquiry.getProduct();
		Store store = product.getStore();
		User seller = store.getUser();
		return GetInquiriesResponse.builder()
			.inquiryId(inquiry.getId())
			.sellerId(seller.getId())
			.registeredUserId(inquiry.getUser().getId())
			.productId(product.getId())
			.storeId(store.getId())
			.inquiryStatus(inquiry.getInquiryStatus().getInquiryStatus().getInquiryStatus())
			.build();
	}

	public static List<GetInquiriesResponse> toResponse(List<Inquiry> list) {
		return list.stream().map(GetInquiriesResponse::toResponse).collect(
			Collectors.toList());
	}

//	public static GetInquiriesResponse toResponse(RegisteredInquiry registeredInquiry) {
//		return GetInquiriesResponse.builder()
//			.inquiryId(registeredInquiry.getInquiryId())
//			.sellerId(registeredInquiry.getSellerId())
//			.registeredUserId(registeredInquiry.getRegisteredUserId())
//			.productId(registeredInquiry.getProductId())
//			.storeId(registeredInquiry.getStoreId())
//			.inquiryStatus(registeredInquiry.getInquiryStatus().getInquiryStatus())
//			.build();
//	}
//
//	public static List<GetInquiriesResponse> toResponse(List<RegisteredInquiry> list) {
//		return list.stream().map(GetInquiriesResponse::toResponse).collect(
//			Collectors.toList());
//	}
}
