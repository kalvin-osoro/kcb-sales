package com.ekenya.rnd.backend.fskcb.AdminModule.models.reqs;

import lombok.Data;

import java.util.Date;

@Data
public class LoadLogFileRequest {

    private Date date;
    private int hour;
    private int index;
}
