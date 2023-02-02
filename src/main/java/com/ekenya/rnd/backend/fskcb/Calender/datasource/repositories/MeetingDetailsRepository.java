package com.ekenya.rnd.backend.fskcb.Calender.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.Calender.datasource.entities.MeetingDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingDetailsRepository extends JpaRepository<MeetingDetailsEntity,Long> {

    MeetingDetailsEntity[] findAllByDsrIdAndDateOfEvent(Long dsrId, String dateOfEvent);
}
