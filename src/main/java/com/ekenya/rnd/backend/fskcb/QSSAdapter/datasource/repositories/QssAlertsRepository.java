package com.ekenya.rnd.backend.fskcb.QSSAdapter.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.QSSAdapter.datasource.entities.QssAlertEntity;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.datasource.entities.QssAlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QssAlertsRepository extends JpaRepository<QssAlertEntity,Long> {

    Optional<QssAlertEntity> findByRefCode(String refCode);

    List<QssAlertEntity> findAllByReceiverId(String receiverId);
    List<QssAlertEntity> findAllByReceiverIdAndStatus(String receiverId, QssAlertStatus status);
}
