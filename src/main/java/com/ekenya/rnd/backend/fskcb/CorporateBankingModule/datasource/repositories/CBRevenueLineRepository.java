package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBRevenueLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CBRevenueLineRepository extends JpaRepository<CBRevenueLineEntity, Long> {
}
