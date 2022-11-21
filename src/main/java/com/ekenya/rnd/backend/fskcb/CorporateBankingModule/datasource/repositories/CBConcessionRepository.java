package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBConcessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CBConcessionRepository extends JpaRepository<CBConcessionEntity,Long> {
}
