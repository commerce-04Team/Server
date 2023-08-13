package com.commerce_04.commerce.Repository.product.entity;

import com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatusType;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id", nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false)
	private CategoryType category;
}
