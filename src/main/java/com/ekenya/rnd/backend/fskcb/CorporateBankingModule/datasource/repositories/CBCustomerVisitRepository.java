package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBCustomerVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CBCustomerVisitRepository extends JpaRepository<CBCustomerVisitEntity, Long> {

    //find all
    @Query("SELECT c FROM CBCustomerVisitEntity c")
    List<CBCustomerVisitEntity> findAll();
   @Query(value = "SELECT * FROM dbo_cb_customer_visits WHERE dsr_id = ?1", nativeQuery = true)
    CBCustomerVisitEntity[] findAllByDsrId(Long dsrId);
}
