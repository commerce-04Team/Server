package com.commerce_04.commerce.Service.wishlist;

import static com.commerce_04.commerce.Repository.wishlist.entity.Wishlist.toEntity;

import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.Repository.product.repository.ProductRepository;
import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Repository.user.Entity.UserRepository;
import com.commerce_04.commerce.Repository.wishlist.repository.WishlistRepository;
import com.commerce_04.commerce.Service.wishlist.exception.WishlistErrorCode;
import com.commerce_04.commerce.Service.wishlist.exception.WishlistException;
import com.commerce_04.commerce.web.dto.wishlist.AddWishlistRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WishlistService {

	private final WishlistRepository wishlistRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public void addWishlist(AddWishlistRequest addWishlistRequest) {

		String inputUserId = addWishlistRequest.getUserId();
		Long inputProductId = addWishlistRequest.getProductId();

		// 존재하는 유저인지 확인해야 함
		// 예외처리 Refactoring 필요
		User user = userRepository.findById(inputUserId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 유저 입니다."));

		// 존재하는 상품인지 확인해야 함
		// 예외처리 Refactoring 필요
		Product product = productRepository.findById(inputProductId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 상품 입니다."));

		// 현재 판매 중인지 확인해야 함


		// 관심 목록에 등록돼 있는지 확인해야 함
		if (isProductExist(user, product)) {
			throw new WishlistException(WishlistErrorCode.PRODUCTS_ON_WISHLIST);
		}

		// 관심 목록에 추가
		wishlistRepository.save(toEntity(user, product));
	}

	private boolean isProductExist(User user, Product product) {
		return wishlistRepository.existsByUserIdAndProductId(
			user.getId(),
			product.getId());
	}
}
