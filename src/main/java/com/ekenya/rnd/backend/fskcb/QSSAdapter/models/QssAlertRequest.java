package com.ekenya.rnd.backend.fskcb.QSSAdapter.models;

import lombok.Data;

@Data
public class QssAlertRequest {
    private String category;
    private String receiver;
    private String title;
    private String message;
}
