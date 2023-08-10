package com.commerce_04.commerce.web.controller.inquiry;

import com.commerce_04.commerce.Service.inquiry.InquiryService;
import com.commerce_04.commerce.web.dto.inquiry.ToInquireRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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


}
