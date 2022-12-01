package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringTargetEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IAcquiringTargetsRepository  extends JpaRepository<AcquiringTargetEntity, Long> {

    @Query(value = "SELECT * FROM dbo_aqc_targets where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    AcquiringTargetEntity[] fetchAllTargetCreatedLast7Days();

    //pick targets value and TargetAchieved where aquiringTargetType is ONBOARDING
    @Query(value = "SELECT targetValue, targetAchievement FROM dbo_aqc_targets where acquiringTargetType = 'ONBOARDING'", nativeQuery = true)
    AcquiringTargetEntity[] fetchAllOnboardingTarget();
@Query(value = "SELECT targetValue, targetAchievement,id FROM dbo_aqc_targets where acquiringTargetType = 'VISITS'", nativeQuery = true)
    AcquiringTargetEntity[] fetchAllVisitsTarget();
@Query(value = "SELECT targetValue, targetAchievement,id FROM dbo_aqc_targets where acquiringTargetType = 'LEADS'", nativeQuery = true)
    AcquiringTargetEntity[] fetchAllLeadsTarget();
@Query(value = "SELECT targetValue, targetAchievement,id FROM dbo_aqc_targets where acquiringTargetType = 'CAMPAINGS'", nativeQuery = true)
    AcquiringTargetEntity[] fetchAllCampaignsTarget();
    @Query(value = "SELECT t FROM dbo_aqc_targets t WHERE t.targetType = ?1",nativeQuery = true)
    AcquiringTargetEntity[] findAllByTargetType(TargetType leads);
}
