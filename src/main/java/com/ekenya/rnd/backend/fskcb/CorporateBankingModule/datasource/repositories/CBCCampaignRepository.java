package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBCCampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CBCCampaignRepository extends JpaRepository<CBCCampaignEntity, Long> {
}
