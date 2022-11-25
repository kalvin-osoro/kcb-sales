package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSCustomerVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PSCustomerVisitRepository extends JpaRepository<PSCustomerVisitEntity, Long> {
   @Query(name = "SELECT * FROM ps_customer_visits WHERE dsr_id = ? 1 ",nativeQuery = true)
    List<PSCustomerVisitEntity> findAllByDsrId(Long dsrId);
}
