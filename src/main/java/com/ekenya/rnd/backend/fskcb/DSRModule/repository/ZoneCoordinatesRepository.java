package com.ekenya.rnd.backend.fskcb.DSRModule.repository;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.ZoneCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneCoordinatesRepository extends JpaRepository<ZoneCoordinates, Long> {

}
