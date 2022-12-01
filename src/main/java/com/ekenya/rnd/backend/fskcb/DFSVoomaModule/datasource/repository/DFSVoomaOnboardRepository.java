package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
@org.springframework.stereotype.Repository
public interface DFSVoomaOnboardRepository extends JpaRepository<DFSVoomaOnboardEntity, Long> {
    @Query(value = "SELECT * FROM dfs_vooma_onboard WHERE created_on >= DATE_SUB(NOW(), INTERVAL 7 DAY)", nativeQuery = true)
    Iterable<DFSVoomaOnboardEntity> findAllByCreatedOn();

//    @Query(value = "SELECT * FROM dfs_vooma_onboard WHERE dsr_id =? 1 ",nativeQuery = true)
//    DFSVoomaOnboardEntity[] findByDsrId(Long dsrId);

    @Query(value = "SELECT * FROM dfs_vooma_onboard WHERE is_approved = true", nativeQuery = true)
    Iterable<DFSVoomaOnboardEntity> findAllByIsApproved();

    //get all onbearded merchants

}