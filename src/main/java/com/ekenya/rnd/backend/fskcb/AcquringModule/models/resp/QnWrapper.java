package com.ekenya.rnd.backend.fskcb.AcquringModule.models.resp;

import io.swagger.models.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class QnWrapper {
    private Long id;
    private String questionnaireTitle;
    private Object nationalId;
    private String accountNumber;
    private String name;
    private ArrayList<QnResWrapper> responses;
    private Date createdOn;
}
