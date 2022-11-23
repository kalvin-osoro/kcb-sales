package com.ekenya.rnd.backend.fskcb.DSRModule.models;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.ExcelImportError;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DSRsExcelImportResult {

    private List<DSRAccountEntity> accounts = new ArrayList<>();

    private List<ExcelImportError> errors = new ArrayList<>();
}
