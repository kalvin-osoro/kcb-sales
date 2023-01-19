package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AcquiringAssetRepository extends JpaRepository<AcquiringAssetEntity, Long> {
    @Query(value = "SELECT * FROM acquiring_asset where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    List< AcquiringAssetEntity> fetchAllAssetsCreatedLast7Days();
    @Query(value = "SELECT count(*) FROM acquiring_asset where created_on >= current_date at time zone 'UTC' - interval '7 days' and status = 'faulty'", nativeQuery = true)
    int countFaultyAssets();
    @Query(value = "SELECT count(*) FROM acquiring_asset where created_on >= current_date at time zone 'UTC' - interval '7 days' and asset_condition = 'WORKING'", nativeQuery = true)
    int countWorkingAssets();

    @Query(value = "SELECT count(*) FROM acquiring_asset where created_on >= current_date at time zone 'UTC' - interval '7 days' and assigned = true", nativeQuery = true)
    int countAssignedAssets();

    @Query(value = "SELECT count(*) FROM acquiring_asset where created_on >= current_date at time zone 'UTC' - interval '7 days' and assigned = false", nativeQuery = true)
    int countUnAssignedAssets();
    @Query(value = "SELECT * FROM acquiring_asset where agent_id = ?1", nativeQuery = true)
    AcquiringAssetEntity[] getAllAgentsAssets(Long agentId);

    @Query(value = "SELECT * FROM acquiring_asset where assetNumber  = ?1", nativeQuery = true)
    AcquiringAssetEntity findByAssetNumber(Long assetNumber);

    Optional<Object> findBySerialNumber(String serialNumber);

    List<AcquiringAssetEntity> findByMerchantAccNo(String merchantAccNo);
}

