package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IAcquiringOnboardingsRepository extends JpaRepository<AcquiringOnboardEntity, Long> {

    @Query(value = "SELECT * FROM dbo_aqc_onboarding where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    AcquiringOnboardEntity[] fetchAllOnboardingCreatedLast7Days();

    @Query(value = "SELECT * FROM dbo_aqc_onboarding WHERE isApproved = true", nativeQuery = true)
    AcquiringOnboardEntity[] findAllByIsApproved();
    @Query(value = "SELECT * FROM dbo_aqc_onboarding WHERE clientLegalName LIKE %?1% OR businessName LIKE %?1% OR  businessPhoneNumber LIKE %?1%  ", nativeQuery = true)
    AcquiringOnboardEntity[] searchCustomers(String keyword);
}
