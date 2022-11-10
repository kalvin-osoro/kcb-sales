package com.ekenya.rnd.backend.fskcb.repository;

import com.ekenya.rnd.backend.fskcb.entity.AssetFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetFilesRepository  extends JpaRepository<AssetFiles, Long> {
}
