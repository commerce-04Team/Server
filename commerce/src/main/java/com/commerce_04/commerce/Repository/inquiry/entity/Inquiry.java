package com.commerce_04.commerce.Repository.inquiry.entity;

import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.web.dto.inquiry.ToInquireRequest;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Inquiry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inquiry_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inquiry_status")
	private InquiryStatus inquiryStatus;

	@Column(name = "text", nullable = false)
	private String text;

	@Column(name = "create_at", nullable = false)
	private LocalDateTime createAt;

	public static Inquiry toEntity(User user, Product product,
		InquiryStatus inquiryStatus, ToInquireRequest toInquireRequest) {
		return Inquiry.builder()
			.user(user)
			.product(product)
			.inquiryStatus(inquiryStatus)
			.text(toInquireRequest.getText())
			.createAt(LocalDateTime.now())
			.build();
	}
}
