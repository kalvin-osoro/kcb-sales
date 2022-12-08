package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringCustomerVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AcquiringCustomerVisitRepository extends JpaRepository<AcquiringCustomerVisitEntity, Long> {

@Query(value = "SELECT * FROM acquiring_customer_visits WHERE dsr_id=?1", nativeQuery = true)
    AcquiringCustomerVisitEntity[] getAllCustomerVisitsByDSR(int dsrId);

    @Query(value = "SELECT COUNT(*) FROM acquiring_customer_visits WHERE dsrId=?1", nativeQuery = true)
    int countTotalVisits(Long dsrId);
}
