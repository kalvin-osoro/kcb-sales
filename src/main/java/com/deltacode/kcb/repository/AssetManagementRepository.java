package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.AssetManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetManagementRepository extends JpaRepository<AssetManagement, Long> {
}
