package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DFSVoomaLeadRepository extends JpaRepository<DFSVoomaLeadEntity, Long> {
    @Query("SELECT l FROM DFSVoomaLeadEntity l WHERE l.dsrId = ?1")
    DFSVoomaLeadEntity[] findAllByDsrId(Long dsrId);

}
