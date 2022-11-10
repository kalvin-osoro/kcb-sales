package com.ekenya.rnd.backend.fskcb.repository;

import com.ekenya.rnd.backend.fskcb.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails,Long> {
    List<CustomerDetails> findCustomerDetailsByCreatedBy(Long createdId);
    List<CustomerDetails> findAllByCreatedOnBetween(Date startDate, Date endDate);
    List<CustomerDetails> findAllByCreatedByAndCreatedOnBetween(long userId, Date startDate, Date endDate);
}
