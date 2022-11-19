package com.ekenya.rnd.backend.fskcb.UserManagement.helper;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.AccountType;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRole;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
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
import java.util.*;

public class ExcelHelper {
    private static final int STAFF_NO_COLUMN_INDEX = 1;

    private static final int FULL_NAME_COLUMN_INDEX = 2;
    private static final int EMAIL_COLUMN_INDEX = 3;
    //private final static int ACCOUNT_TYPE_COLUMN_INDEX = 3;
    private final static int PHONE_NUMBER_COLUMN_INDEX = 4;
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

//
//
//
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

                UserAccount userAccount = new UserAccount();

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
                        userAccount.setStaffNo(staffNo);
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
                        userAccount.setFullName(name);
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
                        userAccount.setPhoneNumber(phoneNo);
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
                        userAccount.setEmail(email);
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
                    result.getAccounts().add(userAccount);
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



