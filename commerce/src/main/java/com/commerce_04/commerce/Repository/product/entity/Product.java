package com.commerce_04.commerce.Repository.product.entity;

import com.commerce_04.commerce.Repository.user.Entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@ManyToOne
	@JoinColumn(name = "category")
	private Category category;

	@OneToMany(mappedBy = "product")
	private List<ProductImage> productImages = new ArrayList<>();

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "contents")
	private String contents;

	@Column(name = "product_status")
	private String productStatus;

	@Column(name = "create_at", nullable = false)
	private LocalDateTime createAt;

	@Column(name = "update_at")
	private LocalDateTime updateAt;

	@Column(name = "delete_at")
	private LocalDateTime deleteAt;

	@Column(name = "is_delete", nullable = false)
	private boolean isDelete;

}
