package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails,Long> {
    List<CustomerDetails> findCustomerDetailsByCreatedBy(Long createdId);
    List<CustomerDetails> findAllByCreatedOnBetween(Date startDate, Date endDate);
    List<CustomerDetails> findAllByCreatedByAndCreatedOnBetween(long userId, Date startDate, Date endDate);
}
