package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcessionRepository extends JpaRepository<ConcessionEntity, Long> {
}
