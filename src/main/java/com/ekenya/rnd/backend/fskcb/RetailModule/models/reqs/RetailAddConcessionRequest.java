package com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs;

import lombok.Data;

@Data
public class RetailAddConcessionRequest {

    private String customerName;

    private String submissionRate;
    private String submittedBy;
}
