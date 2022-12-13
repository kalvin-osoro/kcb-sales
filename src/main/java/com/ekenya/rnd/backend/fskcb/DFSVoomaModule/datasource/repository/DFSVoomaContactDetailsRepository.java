package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaContactDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DFSVoomaContactDetailsRepository extends JpaRepository<DFSVoomaContactDetailsEntity,Long> {
    List<DFSVoomaContactDetailsEntity> findByMerchantId(Long id);
}
