package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DFSVoomaOnboardRepository extends JpaRepository<DFSVoomaOnboardEntity, Long> {

   //findAll
   @Query(value = "SELECT * FROM dfs_vooma_onboard where createdOn >= (current_date - interval 7 day)", nativeQuery = true)
    Iterable<DFSVoomaOnboardEntity> findAllByCreatedOn();

//    @Query(value = "SELECT * FROM dfs_vooma_onboard WHERE dsr_id =? 1 ",nativeQuery = true)
//    DFSVoomaOnboardEntity[] findByDsrId(Long dsrId);

    @Query(value = "SELECT * FROM dfs_vooma_onboard WHERE isApproved = true", nativeQuery = true)
    Iterable<DFSVoomaOnboardEntity> findAllByIsApproved();


}//