package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDSRAccountsRepository extends JpaRepository<DSRAccountEntity, Long> {


    List<DSRAccountEntity> findByStatus(Status status);
    Optional<DSRAccountEntity>findById(long id);
    List<DSRAccountEntity> findAllByTeamId(long teamId);

    Optional<DSRAccountEntity> findByStaffNo(String staffNo);
    Optional<DSRAccountEntity> findByStaffNoAndPhoneNo(String staffNo,String phoneNo);

}
