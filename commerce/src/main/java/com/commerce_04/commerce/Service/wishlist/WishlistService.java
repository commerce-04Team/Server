package com.commerce_04.commerce.Service.wishlist;

import static com.commerce_04.commerce.Repository.wishlist.entity.Wishlist.toEntity;

import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.Repository.product.repository.ProductRepository;
import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Repository.user.Entity.UserRepository;
import com.commerce_04.commerce.Repository.wishlist.entity.Wishlist;
import com.commerce_04.commerce.Repository.wishlist.repository.WishlistRepository;
import com.commerce_04.commerce.Service.wishlist.exception.WishlistErrorCode;
import com.commerce_04.commerce.Service.wishlist.exception.WishlistException;
import com.commerce_04.commerce.web.dto.wishlist.AddWishlistRequest;
import com.commerce_04.commerce.web.dto.wishlist.DeleteWishlistRequest;
import com.commerce_04.commerce.web.dto.wishlist.WishlistResponse;
import java.util.List;
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

		User validatedUser = verifyUser(inputUserId);
		Product validatedProduct = verifyProduct(inputProductId);

		if (checkWishlistExist(validatedUser, validatedProduct)) {
			throw new WishlistException(
				WishlistErrorCode.PRODUCTS_ON_THE_WISHLIST);
		}

		wishlistRepository.save(toEntity(validatedUser, validatedProduct));
	}

	public void deleteWishlist(DeleteWishlistRequest deleteWishlistRequest) {

		String inputUserId = deleteWishlistRequest.getUserId();
		Long inputProductId = deleteWishlistRequest.getProductId();
		Long inputWishlistId = deleteWishlistRequest.getWishlistId();

		User validatedUser = verifyUser(inputUserId);
		Product validatedProduct = verifyProduct(inputProductId);
		Wishlist validatedWishlist = ValidatedWishlist(inputWishlistId);

		if (validatedWishlist.equals(
			isWishlistExist(validatedUser, validatedProduct))) {
			wishlistRepository.delete(validatedWishlist);
		} else {
			throw new WishlistException(
				WishlistErrorCode.PRODUCTS_IS_NOT_ON_THE_WISHLIST);
		}
	}

	public List<WishlistResponse> getWishlist(String userId) {
		verifyUser(userId);
		return WishlistResponse.toResponse(
			wishlistRepository.findMyWishlist(userId));
	}

	private Wishlist ValidatedWishlist(Long inputWishlistId) {
		return wishlistRepository.findById(
				inputWishlistId)
			.orElseThrow(() -> new WishlistException(
				WishlistErrorCode.WISHLIST_ID_DOES_NOT_EXIST));
	}

	private Product verifyProduct(Long inputProductId) {
		return productRepository.findById(inputProductId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 상품 입니다."));
	}

	private User verifyUser(String inputUserId) {
		return userRepository.findById(inputUserId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 유저 입니다."));
	}

	private Wishlist isWishlistExist(User user, Product product) {
		return wishlistRepository.findByUserIdAndProductId(
				user.getId(), product.getId())
			.orElseThrow(() -> new WishlistException(
				WishlistErrorCode.PRODUCTS_IS_NOT_ON_THE_WISHLIST));
	}

	private boolean checkWishlistExist(User user, Product product) {
		return wishlistRepository.existsByUserIdAndProductId(
			user.getId(), product.getId());
	}


}
