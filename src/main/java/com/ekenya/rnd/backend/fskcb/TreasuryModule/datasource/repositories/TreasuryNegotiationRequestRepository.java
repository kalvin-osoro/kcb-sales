package com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryNegotiationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreasuryNegotiationRequestRepository extends JpaRepository<TreasuryNegotiationRequestEntity, Long> {
}
