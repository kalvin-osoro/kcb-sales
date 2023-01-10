package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardingKYCentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcquiringOnboardingKYCRepository extends JpaRepository<AcquiringOnboardingKYCentity, Long> {
    List<AcquiringOnboardingKYCentity> findByMerchantId(Long merchantId);
}
