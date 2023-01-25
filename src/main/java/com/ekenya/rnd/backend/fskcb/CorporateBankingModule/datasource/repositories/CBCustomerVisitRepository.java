package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CBCustomerVisitRepository extends JpaRepository<CBCustomerVisitEntity, Long> {

    //find all
//    @Query("SELECT c FROM CBCustomerVisitEntity c")
//    List<CBCustomerVisitEntity> findAll();
   @Query(value = "SELECT * FROM cb_customer_visits WHERE dsr_id = ?1", nativeQuery = true)
    CBCustomerVisitEntity[] findAllByDsrId(Long dsrId);




    @Query(value = "SELECT COUNT(*) FROM cb_customer_visits WHERE dsrId=?1", nativeQuery = true)
    int countTotalVisits(Long dsrId);
    @Query(value = "SELECT * FROM cb_customer_visits WHERE dsrId = ?1 AND opportunities IS NOT NULL", nativeQuery = true)
    List<CBCustomerVisitEntity> findAllByDsrIdAndOpportunitiesIsNotNull(Long dsrId);
}
