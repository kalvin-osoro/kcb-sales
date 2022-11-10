package com.ekenya.rnd.backend.fskcb.repository;

import com.ekenya.rnd.backend.fskcb.entity.MerchantDetails;
import com.ekenya.rnd.backend.fskcb.entity.MerchantKYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MerchantKYCRepository extends JpaRepository<MerchantKYC,Long> {
    List<MerchantKYC> findByMerchantDetails(MerchantDetails merchantDetails);
}

