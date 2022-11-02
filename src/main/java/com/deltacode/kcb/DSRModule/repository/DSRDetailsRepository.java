package com.deltacode.kcb.DSRModule.repository;

import com.deltacode.kcb.DSRModule.models.DSRDetails;
import com.deltacode.kcb.DSRModule.models.DSRTeam;
import com.deltacode.kcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DSRDetailsRepository extends JpaRepository<DSRDetails, Long> {


    List<DSRDetails> findDSRDetailsByStatus(Status status);
    Optional<DSRDetails>findDSRDetailsBySystemUserId(long id);
    List<DSRDetails> findAllByDsrTeam(DSRTeam dsrTeam);
}
