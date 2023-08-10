package com.commerce_04.commerce.web.controller.inquiry;

import com.commerce_04.commerce.Service.inquiry.InquiryService;
import com.commerce_04.commerce.web.dto.inquiry.AnswerInquireRequest;
import com.commerce_04.commerce.web.dto.inquiry.GetInquiriesResponse;
import com.commerce_04.commerce.web.dto.inquiry.GetInquiryDetailResponse;
import com.commerce_04.commerce.web.dto.inquiry.ToInquireRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/inquiry")
@RestController
public class InquiryController {

	private final InquiryService inquiryService;

	@PostMapping
	public ResponseEntity<?> toInquire(
		@RequestBody ToInquireRequest toInquireRequest
	) {
		inquiryService.toInquire(toInquireRequest);
		return ResponseEntity.ok(
			toInquireRequest.getProductId() + " 상품에 문의를 등록하였습니다.");
	}

	@PostMapping("/answer")
	public ResponseEntity<?> answerInquiry(
		@RequestBody AnswerInquireRequest answerInquireRequest
	) {
		inquiryService.answerInquireDetail(answerInquireRequest);
		return ResponseEntity.ok(
			"inquiryId : " + answerInquireRequest.getInquiryId()
				+ "에 답변이 완료 됐습니다.");
	}

	@GetMapping("/detail")
	public ResponseEntity<GetInquiryDetailResponse> getInquireDetail(
		@RequestParam String userId,
		@RequestParam Long inquiryId
	) {
		return ResponseEntity.ok(
			inquiryService.getInquireDetail(userId, inquiryId));
	}


	@GetMapping
	public ResponseEntity<List<GetInquiriesResponse>> getInquiresSent(
		@RequestParam String userId
	) {
		return ResponseEntity.ok(inquiryService.getInquiresSent(userId));
	}


}
