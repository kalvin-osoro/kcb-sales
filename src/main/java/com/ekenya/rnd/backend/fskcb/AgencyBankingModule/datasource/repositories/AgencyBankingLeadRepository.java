package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgencyBankingLeadRepository extends JpaRepository<AgencyBankingLeadEntity,Long> {

    List<AgencyBankingLeadEntity> findAllByDsrId(Long dsrId);
}
