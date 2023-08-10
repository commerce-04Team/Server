package com.commerce_04.commerce.web.controller.wishlist;

import com.commerce_04.commerce.Service.wishlist.WishlistService;
import com.commerce_04.commerce.web.dto.wishlist.AddWishlistRequest;
import com.commerce_04.commerce.web.dto.wishlist.DeleteWishlistRequest;
import com.commerce_04.commerce.web.dto.wishlist.WishlistResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/wishlist")
@RestController
public class WishlistController {

	private final WishlistService wishlistService;

	@PostMapping
	public ResponseEntity<?> addWishlist(
		@RequestBody AddWishlistRequest addWishlistRequest
	) {
		wishlistService.addWishlist(addWishlistRequest);
		return ResponseEntity.ok("관심 목록에 추가하였습니다!");
	}

	@DeleteMapping
	public ResponseEntity<?> deleteWishlist(
		@RequestBody DeleteWishlistRequest deleteWishlistRequest
	) {
		wishlistService.deleteWishlist(deleteWishlistRequest);
		return ResponseEntity.ok("관심 목록에서 삭제하였습니다!");
	}

	@GetMapping
	public ResponseEntity<List<WishlistResponse>> getWishlist(
		@RequestParam String userId
	) {
		return ResponseEntity.ok(wishlistService.getWishlist(userId));
	}


}
