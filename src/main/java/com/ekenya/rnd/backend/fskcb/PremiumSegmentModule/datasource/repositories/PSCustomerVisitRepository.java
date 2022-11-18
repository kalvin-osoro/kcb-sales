package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSCustomerVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PSCustomerVisitRepository extends JpaRepository<PSCustomerVisitEntity, Long> {
}
