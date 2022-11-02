package com.deltacode.kcb.DSRModule.repository;

import com.deltacode.kcb.DSRModule.models.ZoneCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneCoordinatesRepository extends JpaRepository<ZoneCoordinates, Long> {

}
