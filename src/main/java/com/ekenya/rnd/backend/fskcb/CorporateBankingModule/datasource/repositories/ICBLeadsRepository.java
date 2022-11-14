package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICBLeadsRepository extends JpaRepository<CBLeadEntity,Long> {

}
