package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAgentOnboardV1;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAgentOnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DFSVoomaAgentOnboardV1Repository extends JpaRepository<DFSVoomaAgentOnboardV1,Long> {
    @Query(value = "SELECT * FROM dfs_vooma_Agent_onboardingV1 WHERE isApproved = true", nativeQuery = true)
    Iterable<DFSVoomaAgentOnboardV1> findAllByIsApproved();

    Optional<Object> findByAccountNumber(Long accountNumber);


    Iterable<? extends DFSVoomaAgentOnboardV1> findByOnboardingStatus(OnboardingStatus approved);
    @Query(value = "SELECT * FROM dfs_vooma_Agent_onboardingV1 WHERE organisationName LIKE %?1% OR businessPhoneNumber LIKE %?1% OR  accountName LIKE %?1% and status='APPROVED' ", nativeQuery = true)
    DFSVoomaAgentOnboardV1 searchAgent(String keyword);
}
