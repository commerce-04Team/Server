package com.commerce_04.commerce.Service.inquiry;

import static com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatusType.DO_NOT_READ;

import com.commerce_04.commerce.Repository.inquiry.entity.Inquiry;
import com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatus;
import com.commerce_04.commerce.Repository.inquiry.repository.InquiryRepository;
import com.commerce_04.commerce.Repository.inquiry.repository.InquiryStatusRepository;
import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.Repository.product.repository.ProductRepository;
import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Repository.user.Entity.UserRepository;
import com.commerce_04.commerce.web.dto.inquiry.ToInquireRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class InquiryService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final InquiryRepository inquiryRepository;
	private final InquiryStatusRepository inquiryStatusRepository;

	public void toInquire(ToInquireRequest toInquireRequest) {

		String inputUserId = toInquireRequest.getUserId();
		Long inputProductId = toInquireRequest.getProductId();

		User verifyUser = verifyUser(inputUserId);
		Product validatedProduct = verifyProduct(inputProductId);

		InquiryStatus inquiryStatus = inquiryStatusRepository
			.findByInquiryStatus(DO_NOT_READ);

		inquiryRepository.save(
			Inquiry.toEntity(verifyUser, validatedProduct, inquiryStatus,
				toInquireRequest));
	}

	private User verifyUser(String inputUserId) {
		return userRepository.findById(inputUserId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 유저 입니다."));
	}

	private Product verifyProduct(Long inputProductId) {
		return productRepository.findById(inputProductId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 상품 입니다."));
	}
}
