package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBranchesRepository extends JpaRepository<BranchEntity,Long> {

    Boolean existsByName(String name);

    Boolean existsByCode(String code);
}
