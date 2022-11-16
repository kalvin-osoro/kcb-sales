package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcquiringAssetRepository extends JpaRepository<AcquiringAssetEntity, Long> {
    @Query(value = "SELECT * FROM dbo_aqc_assets WHERE created_on >= DATEADD(day, -7, GETDATE())", nativeQuery = true)
    List< AcquiringAssetEntity> fetchAllAssetsCreatedLast7Days();
//count number of faulty assets
    @Query(value = "SELECT COUNT(*) FROM dbo_aqc_assets WHERE condition = 'FAULTY'", nativeQuery = true)
    int countFaultyAssets();
    //count number of working assets
    @Query(value = "SELECT COUNT(*) FROM dbo_aqc_assets WHERE condition = 'WORKING'", nativeQuery = true)
    int countWorkingAssets();

    //count number of assets where assigned is true
    @Query(value = "SELECT COUNT(*) FROM dbo_aqc_assets WHERE assigned = true", nativeQuery = true)
    int countAssignedAssets();

    //count number of assets where assigned is false
    @Query(value = "SELECT COUNT(*) FROM dbo_aqc_assets WHERE assigned = false", nativeQuery = true)
    int countUnAssignedAssets();
}

