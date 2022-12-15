package com.ekenya.rnd.backend.fskcb.UserManagement.helper;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRRegionEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.DSRsExcelImportResult;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.RegionsExcelImportResult;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.RoleRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.ExcelImportError;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.UsersExcelImportResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelHelper {
    @Autowired
    static PasswordEncoder passwordEncoder;
    @Autowired
    static RoleRepository roleRepository;
    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET = "Users";
    public static boolean hasExcelFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }

    public static UsersExcelImportResult excelToUserAccounts(InputStream is) {


        final int STAFF_NO_COLUMN_INDEX = 1;

        final int FULL_NAME_COLUMN_INDEX = 2;
        final int EMAIL_COLUMN_INDEX = 3;
        final int PHONE_NUMBER_COLUMN_INDEX = 4;
        
        try {
            UsersExcelImportResult result = new UsersExcelImportResult();


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

                UserAccountEntity dsrAccount = new UserAccountEntity();

                //Staff No
                try{
                    Cell currentCell = currentRow.getCell(STAFF_NO_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String staffNo = currentCell.getStringCellValue();
                    if(staffNo == null || staffNo.isEmpty() || staffNo.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Staff No.' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(STAFF_NO_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        dsrAccount.setStaffNo(staffNo);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(STAFF_NO_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }

                //Full Name
                try{

                    Cell currentCell = currentRow.getCell(FULL_NAME_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String name = currentCell.getStringCellValue();
                    if(name == null || name.isEmpty() || name.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Full Name' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(FULL_NAME_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        dsrAccount.setFullName(name);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(FULL_NAME_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }

                //Phone Number
                try{
                    Cell currentCell = currentRow.getCell(PHONE_NUMBER_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String phoneNo = currentCell.getStringCellValue();
                    if(phoneNo == null || phoneNo.isEmpty() || phoneNo.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Phone Number' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(PHONE_NUMBER_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else if(!phoneNo.startsWith("254")){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Phone Number' should start with '254XXX..'. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(PHONE_NUMBER_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        dsrAccount.setPhoneNumber(phoneNo);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(PHONE_NUMBER_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }

                //Email
                try{
                    Cell currentCell = currentRow.getCell(EMAIL_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String email = currentCell.getStringCellValue();
                    if(email == null || email.isEmpty() || email.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Email' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(EMAIL_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        dsrAccount.setEmail(email);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(EMAIL_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }
                //
                if(result.getErrors().isEmpty()) {
                    result.getAccounts().add(dsrAccount);
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


    public static DSRsExcelImportResult excelToDSRAccounts(InputStream is,IDSRTeamsRepository dsrTeamsRepository) {


        final int STAFF_NO_COLUMN_INDEX = 1;

        final int FULL_NAME_COLUMN_INDEX = 2;
        final int EMAIL_COLUMN_INDEX = 3;
        final int PHONE_NUMBER_COLUMN_INDEX = 4;
        final int SALES_CODE_COLUMN_INDEX = 5;
        final int TEAM_COLUMN_INDEX = 6;
        final int GENDER_COLUMN_INDEX = 7;
        final int ACCOUNT_EXPIRY_COLUMN_INDEX = 8;
        
        try {
            DSRsExcelImportResult result = new DSRsExcelImportResult();


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

                DSRAccountEntity dsrAccount = new DSRAccountEntity();

                //Staff No
                try{
                    Cell currentCell = currentRow.getCell(STAFF_NO_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String staffNo = currentCell.getStringCellValue();
                    if(staffNo == null || staffNo.isEmpty() || staffNo.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Staff No.' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(STAFF_NO_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        dsrAccount.setStaffNo(staffNo);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(STAFF_NO_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }

                //Full Name
                try{

                    Cell currentCell = currentRow.getCell(FULL_NAME_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String name = currentCell.getStringCellValue();
                    if(name == null || name.isEmpty() || name.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Full Name' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(FULL_NAME_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        dsrAccount.setFullName(name);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(FULL_NAME_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }

                //Phone Number
                try{
                    Cell currentCell = currentRow.getCell(PHONE_NUMBER_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String phoneNo = currentCell.getStringCellValue();
                    if(phoneNo == null || phoneNo.isEmpty() || phoneNo.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Phone Number' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(PHONE_NUMBER_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else if(!phoneNo.startsWith("254")){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Phone Number' should start with '254XXX..'. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(PHONE_NUMBER_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        dsrAccount.setPhoneNo(phoneNo);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(PHONE_NUMBER_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }

                //Email
                try{
                    Cell currentCell = currentRow.getCell(EMAIL_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String email = currentCell.getStringCellValue();
                    if(email == null || email.isEmpty() || email.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Email' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(EMAIL_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        dsrAccount.setEmail(email);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(EMAIL_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }
                //Team
                try{
                    Cell currentCell = currentRow.getCell(TEAM_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String team = currentCell.getStringCellValue();
                    if(team == null || team.isEmpty() || team.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Team' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(TEAM_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        Optional<DSRTeamEntity> teamEntity = dsrTeamsRepository.findByName(team);
                        if(teamEntity.isPresent()){
                            //
                            dsrAccount.setTeamId(teamEntity.get().getId());
                        }else{
                            //Create a new team or report error
                            ExcelImportError importError = new ExcelImportError();
                            importError.setError("No existing team with name '"+team+"'");
                            importError.setRow(rowNumber);
                            importError.setColumn(TEAM_COLUMN_INDEX);
                            //
                            result.getErrors().add(importError);
                        }
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(TEAM_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }
                //Sales Code
                try{
                    Cell currentCell = currentRow.getCell(SALES_CODE_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String email = currentCell.getStringCellValue();
                    if(email == null || email.isEmpty() || email.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Sales Code' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(SALES_CODE_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        dsrAccount.setSalesCode(email);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(SALES_CODE_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }

                //Sales Code
                try{
                    Cell currentCell = currentRow.getCell(GENDER_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String gender = currentCell.getStringCellValue();
                    if(gender == null || gender.isEmpty() || gender.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Gender' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(GENDER_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        dsrAccount.setGender(gender);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(GENDER_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }

                //Account Expiry
                try{
                    Cell currentCell = currentRow.getCell(ACCOUNT_EXPIRY_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String expiry = currentCell.getStringCellValue();
                    if(expiry != null || !expiry.isEmpty() || !expiry.isBlank()) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        dsrAccount.setExpiryDate(dateFormat.parse(expiry));
                    }else{
                        //Not set
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(ACCOUNT_EXPIRY_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }
                //
                if(result.getErrors().isEmpty()) {
                    result.getAccounts().add(dsrAccount);
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

    public static RegionsExcelImportResult excelToDSRRegions(InputStream is){

        final int REGION_CODE_COLUMN_INDEX = 1;

        final int REGION_NAME_COLUMN_INDEX = 2;

        final int GEO_JSON_BOUNDS_COLUMN_INDEX = 2;


        try {
            RegionsExcelImportResult result = new RegionsExcelImportResult();


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

                DSRRegionEntity regionEntity = new DSRRegionEntity();

                //Staff No
                try{
                    Cell currentCell = currentRow.getCell(REGION_CODE_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String regionCode = currentCell.getStringCellValue();
                    if(regionCode == null || regionCode.isEmpty() || regionCode.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Region Code' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(REGION_CODE_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        regionEntity.setCode(regionCode);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(REGION_CODE_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }

                //Name
                try{

                    Cell currentCell = currentRow.getCell(REGION_NAME_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String name = currentCell.getStringCellValue();
                    if(name == null || name.isEmpty() || name.isBlank()){
                        ExcelImportError importError = new ExcelImportError();
                        importError.setError("Required Field 'Region Name' is missing. Record Skipped.");
                        importError.setRow(rowNumber);
                        importError.setColumn(REGION_NAME_COLUMN_INDEX);
                        //
                        result.getErrors().add(importError);
                    }else{
                        regionEntity.setName(name);
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(REGION_NAME_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }
                //Bounds
                try{

                    Cell currentCell = currentRow.getCell(GEO_JSON_BOUNDS_COLUMN_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String bounds = currentCell.getStringCellValue();
                    if(bounds != null || !bounds.isEmpty() || !bounds.isBlank()){
                        regionEntity.setName(bounds);
                    }else{
                        //Not specified..
                    }
                }catch (Exception ex){
                    ExcelImportError importError = new ExcelImportError();
                    importError.setError(ex.getMessage());
                    importError.setRow(rowNumber);
                    importError.setColumn(REGION_NAME_COLUMN_INDEX);
                    //
                    result.getErrors().add(importError);
                }
                //
                if(result.getErrors().isEmpty()) {
                    result.getRegions().add(regionEntity);
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



