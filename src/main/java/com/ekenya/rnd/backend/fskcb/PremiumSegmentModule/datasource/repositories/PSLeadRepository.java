package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PSLeadRepository extends JpaRepository<PSLeadEntity, Long> {

   @Query(name = "SELECT * FROM ps_banking_concession WHERE dsr_id = ? 1",nativeQuery = true)
    PSLeadEntity[] findAllByDsrId(Long dsrId);
}
