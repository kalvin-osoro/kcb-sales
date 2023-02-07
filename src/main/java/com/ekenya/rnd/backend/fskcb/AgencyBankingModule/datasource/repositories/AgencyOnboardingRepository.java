package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AgencyOnboardingRepository extends JpaRepository<AgencyOnboardingEntity, Long> {
    @Query(value = "SELECT * FROM dbo_agency_bank_onboarding where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    AgencyOnboardingEntity[] fetchAllOnboardingCreatedLast7Days();


    List<AgencyOnboardingEntity> findAllByDsrId(Long dsrId);

    Optional<Object> findByAccountNumber(Integer accountNumber);

    AgencyOnboardingEntity findAgentByagentPhone(String toString);
    @Query(value = "SELECT * FROM dbo_agency_bank_onboarding where agentName LIKE %?1% or agentPhone LIKE %?1% or agentIdNumber LIKE %?1% or accountNumber LIKE %?1% and status='APPROVED'", nativeQuery = true)
    AgencyOnboardingEntity searchAgent(String keyword);

    Optional<Object> findByAgentIdNumber(String agentIdNumber);
    @Query(value = "SELECT * FROM dbo_agency_bank_onboarding where dsrName LIKE %?1%  and status='APPROVED'", nativeQuery = true)
    List<AgencyOnboardingEntity> searchByDsrNameAndStatus(String dsrName, OnboardingStatus approved);
    @Query(value = "SELECT * FROM dbo_agency_bank_onboarding WHERE isApproved = true", nativeQuery = true)
    Iterable<AgencyOnboardingEntity> findAllByIsApproved();
}
