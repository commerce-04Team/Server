package com.commerce_04.commerce.Service.inquiry;

import static com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatusType.ANSWER_COMPLETE;
import static com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatusType.DO_NOT_READ;

import com.commerce_04.commerce.Repository.inquiry.entity.Inquiry;
import com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatus;
import com.commerce_04.commerce.Repository.inquiry.repository.InquiryRepository;
import com.commerce_04.commerce.Repository.inquiry.repository.InquiryStatusRepository;
import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.Repository.product.repository.ProductRepository;
import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Repository.user.Entity.UserRepository;
import com.commerce_04.commerce.Service.inquiry.exception.InquiryErrorCode;
import com.commerce_04.commerce.Service.inquiry.exception.InquiryException;
import com.commerce_04.commerce.web.dto.inquiry.AnswerInquireRequest;
import com.commerce_04.commerce.web.dto.inquiry.GetInquiryDetailResponse;
import com.commerce_04.commerce.web.dto.inquiry.InquiriesReceivedResponse;
import com.commerce_04.commerce.web.dto.inquiry.InquiriesSentResponse;
import com.commerce_04.commerce.web.dto.inquiry.ToInquireRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class InquiryService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final InquiryRepository inquiryRepository;
	private final InquiryStatusRepository inquiryStatusRepository;
	@CacheEvict(value = "inquires", allEntries = true)
	public void toInquire(ToInquireRequest toInquireRequest) {

		String inputUserId = toInquireRequest.getUserId();
		Long inputProductId = toInquireRequest.getProductId();

		User validatedUser = verifyUser(inputUserId);
		Product validatedProduct = verifyProduct(inputProductId);

		InquiryStatus inquiryStatus = inquiryStatusRepository
			.findByInquiryStatus(DO_NOT_READ);

		inquiryRepository.save(
			Inquiry.toEntity(validatedUser, validatedProduct, inquiryStatus,
				toInquireRequest));
	}

	@CacheEvict(value = "inquires", allEntries = true)
	public GetInquiryDetailResponse getInquireDetail(
		String inputUserId,
		Long inputInquiryId) {

		verifyUser(inputUserId);
		Inquiry validatedInquiry = verifyInquiry(inputInquiryId);

		return GetInquiryDetailResponse.toResponse(validatedInquiry);
	}

	@Transactional
	@CacheEvict(value = "inquires", allEntries = true)
	public void answerInquire(AnswerInquireRequest answerInquireRequest) {

		String inputUserId = answerInquireRequest.getUserId();
		Long inputInquiryId = answerInquireRequest.getInquiryId();
		String inputAnswer = answerInquireRequest.getAnswer();

		verifyUser(inputUserId);
		Inquiry validatedInquiry = verifyInquiry(inputInquiryId);

		InquiryStatus inquiryStatus = inquiryStatusRepository
			.findByInquiryStatus(ANSWER_COMPLETE);

		validatedInquiry.setAnswer(inputAnswer);
		validatedInquiry.setAnswerAt(LocalDateTime.now());
		validatedInquiry.setInquiryStatus(inquiryStatus);
	}
	@Cacheable(value = "inquires", key = "#userId")
	public List<InquiriesSentResponse> getInquiresSent(String userId) {
		return InquiriesSentResponse.toResponse(
			inquiryRepository.findAllByUserId(userId));
	}
	@Cacheable(value = "inquires", key = "#userId")
	public List<InquiriesReceivedResponse> getInquiresReceived(String userId) {
		return InquiriesReceivedResponse.toResponse(
			inquiryRepository.findInquiriesReceivedByUserId(userId));
	}

	@CacheEvict(value = "inquires", allEntries = true)
	public void deleteInquiry(String inputUserId, Long inquiryId) {

		verifyUser(inputUserId);
		Inquiry validatedInquiry = verifyInquiry(inquiryId);

		String senderId = validatedInquiry.getUser().getId();

		if (Objects.equals(senderId, inputUserId)) {
			inquiryRepository.delete(validatedInquiry);
		} else {
			throw new InquiryException(
				InquiryErrorCode.YOU_DO_NOT_HAVE_PERMISSION);
		}

	}

	private User verifyUser(String inputUserId) {
		return userRepository.findById(inputUserId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 유저 입니다."));
	}

	private Product verifyProduct(Long inputProductId) {
		return productRepository.findById(inputProductId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 상품 입니다."));
	}

	private Inquiry verifyInquiry(Long inputInquiryId) {
		return inquiryRepository.findById(inputInquiryId)
			.orElseThrow(() -> new InquiryException(
				InquiryErrorCode.NO_INQUIRY_MATCHES));
	}
}
