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
    @Query(value = "SELECT target_value, target_achievement FROM dbo_aqc_targets where acquiring_target_type = 'ONBOARDING'", nativeQuery = true)
    AcquiringTargetEntity[] fetchAllOnboardingTarget();
@Query(value = "SELECT target_value, target_achievement,id FROM dbo_aqc_targets where acquiring_target_type = 'VISITS'", nativeQuery = true)
    AcquiringTargetEntity[] fetchAllVisitsTarget();
@Query(value = "SELECT target_value, target_achievement,id FROM dbo_aqc_targets where acquiring_target_type = 'LEADS'", nativeQuery = true)
    AcquiringTargetEntity[] fetchAllLeadsTarget();
@Query(value = "SELECT target_value, target_achievement,id FROM dbo_aqc_targets where acquiring_target_type = 'CAMPAINGS'", nativeQuery = true)
    AcquiringTargetEntity[] fetchAllCampaignsTarget();
    @Query(value = "SELECT t FROM dbo_aqc_targets t WHERE t.targetType = ?1",nativeQuery = true)
    AcquiringTargetEntity[] findAllByTargetType(TargetType leads);
}
