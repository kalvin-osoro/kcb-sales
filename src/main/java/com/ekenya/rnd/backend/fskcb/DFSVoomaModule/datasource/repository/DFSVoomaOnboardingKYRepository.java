package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardingKYCentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DFSVoomaOnboardingKYRepository extends JpaRepository<DFSVoomaOnboardingKYCentity,Long> {
    List<DFSVoomaOnboardingKYCentity> findByMerchantId(Long merchantId);
}
