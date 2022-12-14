package com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryCurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreasuryCurrencyRepository extends JpaRepository<TreasuryCurrencyEntity,Long> {
}
