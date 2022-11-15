package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAcquiringLeadsRepository extends JpaRepository<AcquiringLeadEntity, Long> {
    @Query(value = "SELECT * FROM dbo_aqc_leads WHERE created_on >= DATEADD(day, -7, GETDATE())", nativeQuery = true)//
    List<AcquiringLeadEntity> fetchAllLeadsCreatedLast7Days();
@Query(value = "SELECT * FROM dbo_aqc_leads WHERE created_on >= DATEADD(day, -7, GETDATE())", nativeQuery = true)//
    int countAllLeadsCreatedLast7Days();

    @Query(value = "SELECT * FROM dbo_aqc_leads WHERE created_on >= DATEADD(day, -7, GETDATE()) AND assigned = true", nativeQuery = true)//
    int countAllLeadsCreatedLast7DaysAssigned();

    //get all leads created last 7 days and leadStatus is open
    @Query(value = "SELECT * FROM dbo_aqc_leads WHERE created_on >= DATEADD(day, -7, GETDATE()) AND lead_status = 'OPEN'", nativeQuery = true)//
    int countAllLeadsCreatedLast7DaysOpen();
    //get all leads created last 7 days and leadStatus is closed
    @Query(value = "SELECT * FROM dbo_aqc_leads WHERE created_on >= DATEADD(day, -7, GETDATE()) AND lead_status = 'CLOSED'", nativeQuery = true)//
    int countAllLeadsCreatedLast7DaysClosed();


}
