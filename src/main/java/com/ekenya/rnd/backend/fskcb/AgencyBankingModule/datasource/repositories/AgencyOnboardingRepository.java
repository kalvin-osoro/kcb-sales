package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

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
    @Query(value = "SELECT * FROM dbo_agency_bank_onboarding where agentName like %?1% or agentPhone like %?1% or agentIdNumber like %?1% or accountNumber like %?1% and status='APPROVED'", nativeQuery = true)
    AgencyOnboardingEntity searchAgent(String keyword);

}
