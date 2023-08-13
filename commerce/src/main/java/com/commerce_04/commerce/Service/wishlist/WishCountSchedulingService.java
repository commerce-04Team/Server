package com.commerce_04.commerce.Service.wishlist;

import com.commerce_04.commerce.Repository.product.entity.Product;
import com.commerce_04.commerce.Repository.product.repository.ProductRepository;
import com.commerce_04.commerce.Repository.wishlist.entity.WishCountScheduling;
import com.commerce_04.commerce.Repository.wishlist.repository.WishCountSchedulingRepository;
import com.commerce_04.commerce.Repository.wishlist.repository.WishlistRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WishCountSchedulingService {

	private final WishCountSchedulingRepository wishCountSchedulingRepository;
	private final WishlistRepository wishlistRepository;
	private final ProductRepository productRepository;

	@Async
	@Scheduled(cron = "0 0 3 * * *")
	@Transactional
	public void WishCountSum() {

		List<WishCountScheduling> schedules = wishCountSchedulingRepository.findAll();

		CompletableFuture<Void> allOf = CompletableFuture.allOf(
			schedules.stream()
				.map(this::processScheduleAsync)
				.toArray(CompletableFuture[]::new));
		allOf.join();
	}

	private CompletableFuture<Void> processScheduleAsync(
		WishCountScheduling schedule) {

		return CompletableFuture.runAsync(() -> {
			Optional<Product> optionalProduct = productRepository.findById(
				schedule.getProduct().getId());

			// Scheduling 전에 상품이 삭제 됐을 경우
			if (optionalProduct.isEmpty()) {
				wishCountSchedulingRepository.delete(schedule);
				return;
			}
			Product product = optionalProduct.get();
			Long wishCountSum = wishlistRepository.countByProductId(
				product.getId());
			product.setWishCount(wishCountSum);

			wishCountSchedulingRepository.delete(schedule);

			//병렬 처리 중 데드락 상황에 대한 처리
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
	}
}
