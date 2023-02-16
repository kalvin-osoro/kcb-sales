package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaMerchantOnboardV1;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DFSVoomaMerchantOnboardV1Repository extends JpaRepository<DFSVoomaMerchantOnboardV1, Long> {
    @Query(value = "SELECT * FROM dbo_dfs_vooma_merchant_onboard WHERE isApproved = true", nativeQuery = true)
    Iterable<DFSVoomaMerchantOnboardV1> findAllByIsApproved();

    Optional<Object> findByAccountNumber(Integer accountNumber);
    @Query(value = "SELECT * FROM dbo_dfs_vooma_merchant_onboard WHERE tradingName LIKE %?1% OR businessName LIKE %?1% OR  accountName LIKE %?1% ", nativeQuery = true)
    DFSVoomaMerchantOnboardV1 searchAgent(String keyword);


    DFSVoomaMerchantOnboardV1[] findByBusinessNameContainingIgnoreCase(String keyword);
}
