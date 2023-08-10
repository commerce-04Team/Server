package com.commerce_04.commerce.Repository.inquiry.repository;

import com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatus;
import com.commerce_04.commerce.Repository.inquiry.entity.InquiryStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryStatusRepository extends JpaRepository<InquiryStatus, Long> {

	InquiryStatus findByInquiryStatus(InquiryStatusType inquiryStatus);
}
