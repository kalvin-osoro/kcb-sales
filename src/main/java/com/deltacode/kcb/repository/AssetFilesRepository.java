package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.AssetFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetFilesRepository  extends JpaRepository<AssetFiles, Long> {
}
