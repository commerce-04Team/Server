package com.commerce_04.commerce.Repository.inquiry.repository;

import com.commerce_04.commerce.Repository.inquiry.entity.Inquiry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

	List<Inquiry> findAllByUserId(String userId);

	@Query(
		"SELECT new com.commerce_04.commerce.Repository.inquiry.repository"
			+ ".InquiriesReceived(i.id, recipient.id, sender.id, p.id, s.id, st.inquiryStatus) "
			+ "FROM Inquiry i "
			+ "JOIN i.user sender "
			+ "JOIN i.product p "
			+ "JOIN p.store s "
			+ "JOIN s.user recipient "
			+ "JOIN i.inquiryStatus st "
			+ "WHERE recipient.id = :userId")
	List<InquiriesReceived> findInquiriesReceivedByUserId(String userId);

	@Query(
		"SELECT COUNT(u) FROM Inquiry i "
			+ "JOIN i.product p "
			+ "JOIN p.store s "
			+ "JOIN s.user u "
			+ "WHERE u.id = :userId")
	Long checkRecipients(String userId);
}
