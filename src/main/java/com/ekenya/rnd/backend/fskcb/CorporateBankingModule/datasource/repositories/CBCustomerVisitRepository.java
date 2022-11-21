package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBCustomerVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CBCustomerVisitRepository extends JpaRepository<CBCustomerVisitEntity, Long> {
}
