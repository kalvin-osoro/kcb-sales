package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAgentOnboardV1;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAgentOnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DFSVoomaAgentOnboardV1Repository extends JpaRepository<DFSVoomaAgentOnboardV1,Long> {
    @Query(value = "SELECT * FROM dfs_vooma_Agent_onboardingV1 WHERE isApproved = true", nativeQuery = true)
    Iterable<DFSVoomaAgentOnboardV1> findAllByIsApproved();
}
