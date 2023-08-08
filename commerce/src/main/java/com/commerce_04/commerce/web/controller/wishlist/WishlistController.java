package com.commerce_04.commerce.web.controller.wishlist;

import com.commerce_04.commerce.Repository.wishlist.entity.Wishlist;
import com.commerce_04.commerce.Service.wishlist.WishlistService;
import com.commerce_04.commerce.web.dto.wishlist.AddWishlistRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/wishlist")
@RestController
public class WishlistController {

	private final WishlistService wishlistService;

	@PostMapping
	public ResponseEntity<?> addWishlist(
		AddWishlistRequest addWishlistRequest
	) {
		wishlistService.addWishlist(addWishlistRequest);
		return ResponseEntity.ok("관심 목록에 추가하였습니다!");
	}

}
