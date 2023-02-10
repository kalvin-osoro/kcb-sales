package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardEntity;
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


//    AgencyOnboardingEntity findByAgentNameEqualsIgnoreCaseAndAgentPhoneAndAgentIdNumberAndAccountNumberAndStatus(String keyword);
    Optional<Object> findByAgentIdNumber(String agentIdNumber);
    @Query(value = "SELECT * FROM dbo_agency_bank_onboarding where dsrName LIKE %?1%  and status='APPROVED'", nativeQuery = true)
    List<AgencyOnboardingEntity> searchByDsrNameAndStatus(String keyword, OnboardingStatus approved);
    List<AgencyOnboardingEntity>findByDsrNameEqualsIgnoreCaseAndStatus(String dsrName,OnboardingStatus approved);
    @Query(value = "SELECT * FROM dbo_agency_bank_onboarding WHERE isApproved = true", nativeQuery = true)
    Iterable<AgencyOnboardingEntity> findAllByIsApproved();
//    @Query(value = "SELECT * FROM dbo_agency_bank_onboarding where agentName LIKE %?1% OR agentPhone LIKE %?1% OR agentIdNumber LIKE %?1% OR accountNumber LIKE %?1% and status='APPROVED'", nativeQuery = true)
//@Query("SELECT p FROM AgencyOnboardingEntity p WHERE " +
//        "p.agentName LIKE CONCAT('%',:query, '%')" +
//        "Or p.agentPhone LIKE CONCAT('%', :query, '%')")
//    AgencyOnboardingEntity searchAgent(String query);

    AgencyOnboardingEntity[] findByDsrId(Long dsrId);

    AgencyOnboardingEntity[] findByAgentNameContainingIgnoreCase(String keyword);
}
