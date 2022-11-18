package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PSBankingFeedBackRequest {
    private Long id;
    private String customerId;
    private String channel;
    private String visitRef;
    private String customerName;
    private Integer noOfQuestionAsked;
    private String questionAsked;
    private Date createdOn;
}
