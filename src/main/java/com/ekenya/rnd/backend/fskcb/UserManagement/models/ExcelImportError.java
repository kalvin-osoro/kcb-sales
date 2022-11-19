package com.ekenya.rnd.backend.fskcb.UserManagement.models;

import com.ekenya.rnd.backend.fskcb.UserManagement.services.ExcelService;
import lombok.Data;

@Data
public class ExcelImportError{
    private int row;
    private int column;
    private String error;
    public ExcelImportError(){

    }
    public ExcelImportError(String e){
        this.error = e;
    }
    public ExcelImportError(int row,int col,String e){
        this.row = row;
        this.column = col;
        this.error = e;
    }
}
