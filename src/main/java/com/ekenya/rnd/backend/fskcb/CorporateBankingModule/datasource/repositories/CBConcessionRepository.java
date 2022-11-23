package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBConcessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CBConcessionRepository extends JpaRepository<CBConcessionEntity,Long> {
   @Query("SELECT c FROM CBConcessionEntity c WHERE c.customerAccountNumber = ?1")
    CBConcessionEntity[] findAllByCustomerAccountNumber(String customerAccountNumber);
}
