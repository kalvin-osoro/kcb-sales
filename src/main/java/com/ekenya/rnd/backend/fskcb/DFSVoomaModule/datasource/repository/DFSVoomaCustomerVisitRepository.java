package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaCustomerVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DFSVoomaCustomerVisitRepository extends JpaRepository<DFSVoomaCustomerVisitEntity,Long> {

    List<DFSVoomaCustomerVisitEntity> findAllByDsrId(Long dsrId);

    @Query(value = "SELECT COUNT(*) FROM dfsvooma_customer_visits WHERE dsrId=?1", nativeQuery = true)
    int countTotalVisits(Long dsrId);
}
