package com.ekenya.rnd.backend.fskcb.DSRModule.repository;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.DSRDetails;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.DSRTeam;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DSRDetailsRepository extends JpaRepository<DSRDetails, Long> {


    List<DSRDetails> findDSRDetailsByStatus(Status status);
    Optional<DSRDetails>findDSRDetailsBySystemUserId(long id);
    List<DSRDetails> findAllByDsrTeam(DSRTeam dsrTeam);

    Optional<DSRDetails> findDSRDetailsByUsername(String username);
}
