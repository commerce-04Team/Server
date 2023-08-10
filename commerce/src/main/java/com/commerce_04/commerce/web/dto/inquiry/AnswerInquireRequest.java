package com.commerce_04.commerce.web.dto.inquiry;

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
public class AnswerInquireRequest {

	private String userId;
	private Long inquiryId;
	private String answer;

}
