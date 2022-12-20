package com.ekenya.rnd.backend.fskcb.AdminModule.models.resp;

import lombok.Data;

import java.io.File;

@Data
public class LogFileResponse {

    private String fileName;

    private File content;
}
