package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DFSVoomaAssetRepository extends JpaRepository<DFSVoomaAssetEntity, Long> {


    @Query(value = "SELECT * FROM dfs_vooma_asset WHERE dfsVoomaOnboardEntity_id =?1 AND assetType =?2", nativeQuery = true)
    List<DFSVoomaAssetEntity> findAllByDfsVoomaOnboardingEntityId(Long merchantId);

}
