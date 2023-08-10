package com.commerce_04.commerce.Repository.inquiry.repository;

import com.commerce_04.commerce.Repository.inquiry.entity.Inquiry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

	//	@Query(
//		"SELECT new com.commerce_04.commerce.Repository.inquiry.repository" +
//			".RegisteredInquiry(i.id, seller.id, registeredUser.id, p.id, s.id, is.inquiryStatus) " +
//			"FROM Inquiry i " +
//			"JOIN i.user registeredUser " +
//			"JOIN i.product p " +
//			"JOIN p.store s " +
//			"JOIN s.user seller " +
//			"JOIN i.inquiryStatus is " +
//			"WHERE registeredUser.id = :userId")
	List<Inquiry> findAllByUserId(String userId);
}
