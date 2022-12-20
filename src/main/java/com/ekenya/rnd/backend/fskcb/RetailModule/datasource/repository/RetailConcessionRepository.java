package com.ekenya.rnd.backend.fskcb.RetailModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites.RetailConcessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetailConcessionRepository extends JpaRepository<RetailConcessionEntity, Long> {
}
