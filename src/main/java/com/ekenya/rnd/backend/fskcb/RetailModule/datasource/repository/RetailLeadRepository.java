package com.ekenya.rnd.backend.fskcb.RetailModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites.RetailLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RetailLeadRepository extends JpaRepository<RetailLeadEntity, Long> {
 @Query(value = "SELECT * FROM dbo_retail_leads WHERE dsr_id = ?1", nativeQuery = true)
    RetailLeadEntity[] findAllByDsrId(Long dsrId);
}
