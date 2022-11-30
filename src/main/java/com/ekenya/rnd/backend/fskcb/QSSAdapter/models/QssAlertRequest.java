package com.ekenya.rnd.backend.fskcb.QSSAdapter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QssAlertRequest {
    private String category;
    private String receiver;
    private String title;
    private String message;
}
