package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.ExcelImportError;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AgentExcelImportResult {
    private List<AgencyOnboardingEntity> agents = new ArrayList<>();
    private List<ExcelImportError> errors = new ArrayList<>();
}
