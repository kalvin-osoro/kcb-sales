package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBBankingFeedBackRequest {
    private Long id;
    private String customerId;
    private String channel;
    private String visitRef;
    private String customerName;
    private Integer noOfQuestionAsked;
    private String questionAsked;
    private Date createdOn;
}
