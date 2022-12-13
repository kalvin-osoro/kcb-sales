package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOwnerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DFSVoomaOwnerDetailsRepository extends JpaRepository<DFSVoomaOwnerDetailsEntity,Long> {
    @Query(name = "SELECT * FROM dfs_vooma_owner_details WHERE merchantOnboardId = ?1", nativeQuery = true)
    List<DFSVoomaOwnerDetailsEntity> findByDfsVoomaMerchantOnboardV1(Long id);

    List<DFSVoomaOwnerDetailsEntity> findByMerchantId(Long merchantId);
}
