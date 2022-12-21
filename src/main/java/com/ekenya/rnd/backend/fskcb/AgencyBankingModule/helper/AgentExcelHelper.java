package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.helper;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.AgencyOnboardingRepository;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.AgentExcelImportResult;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.ExcelImportError;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.UsersExcelImportResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Logger;

public class AgentExcelHelper {
    @Autowired
    AgencyOnboardingRepository agencyOnboardingRepository;

    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET = "Agents";

    public static boolean hasExcelFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }

    private static Logger mLogger = Logger.getLogger(AgentExcelImportResult.class.getName());

    public static AgentExcelImportResult excelToAgents(InputStream is) {
        final int REGION_COL = 1;

        final int BRANCH_COL = 2;
        final int STAFF_NAME_COL = 3;
        final int PHONE_NO_COL = 4;
        final int AGENT_NAME_COL = 5;
        final int LATITUDE_COL = 6;
        final int LONGITUDE_COL = 7;

        try {
            AgentExcelImportResult result = new AgentExcelImportResult();


            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                AgencyOnboardingEntity agencyOnboarding = new AgencyOnboardingEntity();

                //region
                try {
                    Cell currentCell = currentRow.getCell(REGION_COL, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String region = currentCell.getStringCellValue();
                    if (region == null || region.isEmpty() || region.isBlank()) {
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Region .' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(REGION_COL);
                        //
                        result.getErrors().add(importError);
                    } else {
                        agencyOnboarding.setRegion(region);
                    }
                } catch (Exception ex) {
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(REGION_COL);
                    //
                    result.getErrors().add(importError);
                }

                //branch
                try {
                    Cell currentCell = currentRow.getCell(BRANCH_COL, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String branch = currentCell.getStringCellValue();
                    if (branch == null || branch.isEmpty() || branch.isBlank()) {
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Branch .' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(BRANCH_COL);
                        //
                        result.getErrors().add(importError);
                    } else {
                        agencyOnboarding.setBranch(branch);
                    }
                } catch (Exception ex) {
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(BRANCH_COL);
                    //
                    result.getErrors().add(importError);
                }
                //staff name
                try {
                    Cell currentCell = currentRow.getCell(STAFF_NAME_COL, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String staffName = currentCell.getStringCellValue();
                    if (staffName == null || staffName.isEmpty() || staffName.isBlank()) {
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Staff Name .' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(STAFF_NAME_COL);
                        //
                        result.getErrors().add(importError);
                    } else {
                        agencyOnboarding.setDsrName(staffName);
                    }
                } catch (Exception ex) {
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(STAFF_NAME_COL);
                    //
                    result.getErrors().add(importError);
                }
                //phone no
                try {
                    Cell currentCell = currentRow.getCell(PHONE_NO_COL, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String phoneNo = currentCell.getStringCellValue();
                    if (phoneNo == null || phoneNo.isEmpty() || phoneNo.isBlank()) {
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Phone No .' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(PHONE_NO_COL);
                        //
                        result.getErrors().add(importError);
                    } else {
                        agencyOnboarding.setAgentPhone(phoneNo);
                    }
                } catch (Exception ex) {
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(PHONE_NO_COL);
                    //
                    result.getErrors().add(importError);
                }
                //agent name
                try {
                    Cell currentCell = currentRow.getCell(AGENT_NAME_COL, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String agentName = currentCell.getStringCellValue();
                    if (agentName == null || agentName.isEmpty() || agentName.isBlank()) {
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Agent Name .' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(AGENT_NAME_COL);
                        //
                        result.getErrors().add(importError);
                    } else {
                        agencyOnboarding.setAgentName(agentName);
                    }
                } catch (Exception ex) {
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(AGENT_NAME_COL);
                    //
                    result.getErrors().add(importError);
                }
                //latitude
                try {
                    Cell currentCell = currentRow.getCell(LATITUDE_COL, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String latitude = currentCell.getStringCellValue();
                    if (latitude == null || latitude.isEmpty() || latitude.isBlank()) {
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Latitude .' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(LATITUDE_COL);
                        //
                        result.getErrors().add(importError);
                    } else {
                        agencyOnboarding.setLatitude(latitude);
                    }
                } catch (Exception ex) {
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(LATITUDE_COL);
                    //
                    result.getErrors().add(importError);
                }
                //longitude
                try {
                    Cell currentCell = currentRow.getCell(LONGITUDE_COL, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String longitude = currentCell.getStringCellValue();
                    if (longitude == null || longitude.isEmpty() || longitude.isBlank()) {
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Longitude .' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(LONGITUDE_COL);
                        //
                        result.getErrors().add(importError);
                    } else {
                        agencyOnboarding.setLongitude(longitude);
                    }
                } catch (Exception ex) {
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(LONGITUDE_COL);
                    //
                    result.getErrors().add(importError);
                }
                if (result.getErrors().isEmpty()) {
                    result.getAgents().add(agencyOnboarding);
                }
            }
            //
            workbook.close();
            //
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }
}
