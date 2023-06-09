package com.ekenya.rnd.backend.fskcb.UserManagement.models;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UsersExcelImportResult {

    private List<UserAccountEntity> accounts = new ArrayList<>();

    private List<ExcelImportError> errors = new ArrayList<>();
}
