package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.MerchantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface MerchantDetailsRepository extends JpaRepository<MerchantDetails,Long> {
    Optional<MerchantDetails>findMerchantDetailsByAccountId(String accountId);
  @Query (value = "select m.phoneNo, m.longitude, m.latitude, m.businessName from MerchantDetails m WHERE m.accountId =?1")
    Optional<MerchantDetails>findGeoMapDetails(String accountId);
    List<MerchantDetails>findMerchantDetailsByCreatedBy(Long createdBy);
    List<MerchantDetails> findAllByCreatedOnBetween(Date startDate, Date endDate);
    List<MerchantDetails> findAllByCreatedByAndCreatedOnBetween(long createdBy,Date startDate, Date endDate );
}