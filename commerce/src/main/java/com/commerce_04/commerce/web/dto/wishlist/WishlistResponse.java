package com.commerce_04.commerce.web.dto.wishlist;

import com.commerce_04.commerce.Repository.wishlist.repository.MyWishlist;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponse {

	private Long wishlistId;
	private Long productId;
	private Long storeId;
	private String sellerId;
	private String title;
	private Integer price;
	private String productStatus;

	public static WishlistResponse toResponse(MyWishlist myWishlist) {
		return WishlistResponse.builder()
			.wishlistId(myWishlist.getWishlistId())
			.productId(myWishlist.getProductId())
			.storeId(myWishlist.getStoreId())
			.title(myWishlist.getTitle())
			.price(myWishlist.getPrice())
			.productStatus(myWishlist.getProductStatus())
			.build();
	}

	public static List<WishlistResponse> toResponse(List<MyWishlist> list) {
		return list.stream().map(WishlistResponse::toResponse).collect(
			Collectors.toList());
	}
}
