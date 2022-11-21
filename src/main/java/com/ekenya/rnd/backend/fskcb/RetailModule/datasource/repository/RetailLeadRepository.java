package com.ekenya.rnd.backend.fskcb.RetailModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites.RetailLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetailLeadRepository extends JpaRepository<RetailLeadEntity, Long> {
}
