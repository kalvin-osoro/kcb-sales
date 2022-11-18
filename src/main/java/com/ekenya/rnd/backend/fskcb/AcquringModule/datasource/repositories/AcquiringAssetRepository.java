package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcquiringAssetRepository extends JpaRepository<AcquiringAssetEntity, Long> {
    @Query(value = "SELECT * FROM dbo_aqc_assets where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    List< AcquiringAssetEntity> fetchAllAssetsCreatedLast7Days();
//count number of faulty assets
    @Query(value = "SELECT count(*) FROM dbo_aqc_assets where created_on >= current_date at time zone 'UTC' - interval '7 days' and status = 'faulty'", nativeQuery = true)
    int countFaultyAssets();
    //count number of working assets
    @Query(value = "SELECT count(*) FROM dbo_aqc_assets where created_on >= current_date at time zone 'UTC' - interval '7 days' and asset_condition = 'WORKING'", nativeQuery = true)
    int countWorkingAssets();

    //count number of assets where assigned is true
    @Query(value = "SELECT count(*) FROM dbo_aqc_assets where created_on >= current_date at time zone 'UTC' - interval '7 days' and assigned = true", nativeQuery = true)
    int countAssignedAssets();

    //count number of assets where assigned is false
    @Query(value = "SELECT count(*) FROM dbo_aqc_assets where created_on >= current_date at time zone 'UTC' - interval '7 days' and assigned = false", nativeQuery = true)
    int countUnAssignedAssets();
}

