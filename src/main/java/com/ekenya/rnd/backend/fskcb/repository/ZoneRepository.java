package com.ekenya.rnd.backend.fskcb.repository;

import com.ekenya.rnd.backend.fskcb.entity.Zone;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Zone findByZoneCode(String zoneCode);

    List<Zone> findAllByStatus(Status active);
}
