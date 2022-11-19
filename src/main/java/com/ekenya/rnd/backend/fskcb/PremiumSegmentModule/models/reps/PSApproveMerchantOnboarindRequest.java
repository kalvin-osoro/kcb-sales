package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PSApproveMerchantOnboarindRequest {
    private Long id;
    private String customerT24Number;
    private String remarks;
    private String customerName;
    private String customerCode;
    private String region;
    private  String customerIdNumber;
    private String customerEmail;
    private String customerPhone;
    private String customerDob;
    private String customerPbox;
    private String customerPostalCode;
    private Long dsrId;
    private Date createdOn;
    private String latitude;
    private String longitude;
    private String businessName;
    private String town;
    private String streetName;
    private String buildingName;
    private String roomNumber;
}
