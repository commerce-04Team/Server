package com.commerce_04.commerce.Repository.inquiry.repository;

import com.commerce_04.commerce.Repository.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

}
