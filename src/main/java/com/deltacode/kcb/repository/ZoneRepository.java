package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.Zone;
import com.deltacode.kcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Zone findByZoneCode(String zoneCode);

    List<Zone> findAllByStatus(Status active);
}
