package com.ekenya.rnd.backend.fskcb.repository;

import com.ekenya.rnd.backend.fskcb.entity.Constituency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConstituencyRepository extends JpaRepository<Constituency, Long> {

    List<Constituency> findByCountyId(long countyId);
}
