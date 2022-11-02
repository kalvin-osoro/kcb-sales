package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.MerchantDetails;
import com.deltacode.kcb.entity.MerchantKYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MerchantKYCRepository extends JpaRepository<MerchantKYC,Long> {
    List<MerchantKYC> findByMerchantDetails(MerchantDetails merchantDetails);
}

