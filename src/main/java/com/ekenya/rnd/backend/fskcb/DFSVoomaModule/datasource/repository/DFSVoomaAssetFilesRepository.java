package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAssetFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DFSVoomaAssetFilesRepository extends JpaRepository<DFSVoomaAssetFilesEntity,Long> {

    List<DFSVoomaAssetFilesEntity> findByIdAsset(Long assetId);
}
