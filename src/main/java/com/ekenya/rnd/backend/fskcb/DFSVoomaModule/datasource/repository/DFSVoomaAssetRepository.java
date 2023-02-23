package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DFSVoomaAssetRepository extends JpaRepository<DFSVoomaAssetEntity, Long> {


    @Query(value = "SELECT * FROM dfs_vooma_asset WHERE dfsVoomaOnboardEntity_id =?1", nativeQuery = true)
    List<DFSVoomaAssetEntity> findAllByDfsVoomaOnboardingEntityId(Long customerId);

    Optional<Object> findBySerialNumber(String serialNumber);

    List<DFSVoomaAssetEntity> findByMerchantAccNo(Integer merchantAccNo);

//    List<DFSVoomaAssetEntity> findByAgentAccNo(Integer merchantAccNo);
    //merchantAccNo

}
