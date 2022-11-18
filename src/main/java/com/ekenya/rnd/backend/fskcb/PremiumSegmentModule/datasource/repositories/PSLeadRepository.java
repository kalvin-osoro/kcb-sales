package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PSLeadRepository extends JpaRepository<PSLeadEntity, Long> {

}
