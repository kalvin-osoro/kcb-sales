package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DFSVoomaOnboardRepository extends JpaRepository<DFSVoomaOnboardEntity, Long> {
   //load all onboarding where createdOn is within the last 7 days
    @Query(value = "SELECT * FROM dfs_vooma_onboard WHERE created_on >= DATE_SUB(NOW(), INTERVAL 7 DAY)", nativeQuery = true)
    Iterable<DFSVoomaOnboardEntity> findAllByCreatedOn();
}