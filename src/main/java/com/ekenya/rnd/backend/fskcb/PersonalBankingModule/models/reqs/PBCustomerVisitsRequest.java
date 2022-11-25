package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PBCustomerVisitsRequest {
    private Long id;
    private  Long dsrId;
    private String dsrName;
    private String customerName;
    private String reasonForVisit;
    private String visitDate;
    private Status status;
    //    private Zone zone;
    private Date createdOn;
    private Date updatedOn;

    private String typeOfVisit;
    private String channelUsed;
    private String actionplan;
    private String highlightOfVisit;
}
