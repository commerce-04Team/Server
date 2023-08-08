package com.commerce_04.commerce.Service.wishlist;

import com.commerce_04.commerce.web.dto.wishlist.AddWishlistRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WishlistService {

	public void addWishlist(AddWishlistRequest addWishlistRequest) {
		// 존재하는 유저인지 확인해야 함
		// 존재하는 상품인지 확인해야 함
		// 현재 판매 중인지 확인해야 함
		// 관심 목록에 등록돼 있는지 확인해야 함
	}
}
