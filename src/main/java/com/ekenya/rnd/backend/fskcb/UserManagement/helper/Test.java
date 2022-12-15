package com.ekenya.rnd.backend.fskcb.UserManagement.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Test {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        System.out.println(dateFormat.parse("28-AUG-2019"));
    }
}
