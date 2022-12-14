package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaMerchantOnboardV1;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DFSVoomaMerchantOnboardV1Repository extends JpaRepository<DFSVoomaMerchantOnboardV1, Long> {
    @Query(value = "SELECT * FROM dbo_dfs_vooma_merchant_onboard WHERE isApproved = true", nativeQuery = true)
    Iterable<DFSVoomaMerchantOnboardV1> findAllByIsApproved();

//    List<DFSVoomaMerchantOnboardV1> findAllByOnboardingStatus(OnboardingStatus onboardingStatus);

}
