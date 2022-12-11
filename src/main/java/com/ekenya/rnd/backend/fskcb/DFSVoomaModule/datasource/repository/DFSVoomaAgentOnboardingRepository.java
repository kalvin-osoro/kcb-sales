package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAgentOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DFSVoomaAgentOnboardingRepository extends JpaRepository<DFSVoomaAgentOnboardingEntity, Long> {
    @Query(value = "SELECT * FROM dfs_vooma_agent_onboarding WHERE isApproved = true", nativeQuery = true)
    Iterable<DFSVoomaAgentOnboardingEntity> findAllByIsApproved();


}
