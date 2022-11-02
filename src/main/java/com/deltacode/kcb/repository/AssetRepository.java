package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.Asset;
import com.deltacode.kcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findAssetByStatus(Status status);

}
