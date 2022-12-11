package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DFSVoomaOnboardRepository extends JpaRepository<DFSVoomaOnboardEntity, Long> {

   //findAll
   @Query(value = "SELECT * FROM dfs_vooma_onboard where createdOn >= (current_date - interval 7 day)", nativeQuery = true)
    Iterable<DFSVoomaOnboardEntity> findAllByCreatedOn();

//    @Query(value = "SELECT * FROM dfs_vooma_onboard WHERE dsr_id =? 1 ",nativeQuery = true)
//    DFSVoomaOnboardEntity[] findByDsrId(Long dsrId);

    @Query(value = "SELECT * FROM dfs_vooma_onboard WHERE isApproved = true", nativeQuery = true)
    Iterable<DFSVoomaOnboardEntity> findAllByIsApproved();
    short countByStatus(OnboardingStatus status);

    @Query(value = "SELECT count(*) FROM dfs_vooma_onboard WHERE status = ?1 AND createdOn >= (current_date - interval 7 day)", nativeQuery = true)
    int countByStatusForLast7Days(OnboardingStatus onboardingStatus);

}