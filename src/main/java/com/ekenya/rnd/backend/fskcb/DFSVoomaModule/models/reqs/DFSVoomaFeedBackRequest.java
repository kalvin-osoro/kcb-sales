package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DFSVoomaFeedBackRequest {
    private Long id;
    private String customerId;
    private String channel;
    private String visitRef;
    private String customerName;
    private Integer noOfQuestionAsked;
    private String questionAsked;
    private Date createdOn;
}
