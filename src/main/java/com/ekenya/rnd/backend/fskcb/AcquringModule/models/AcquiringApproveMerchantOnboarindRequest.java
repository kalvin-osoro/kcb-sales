package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import javax.persistence.Column;
import java.util.Date;

public class AcquiringApproveMerchantOnboarindRequest {

    private String merchantT24Account;
    private String customerT24Number;
    private String remarks;
    private String merchantName;
    private String merchantCode;
    private String region;
    private  String merchantIdNumber;
    private String merchantEmail;
    private String merchantPhone;
    @Column(name = "merchant_dob")
    private String merchantDob;
    private String merchantPbox;
    private String merchantPostalCode;
    private Long dsrId;
    private Date createdOn;
    private String latitude;
    private String longitude;
    private String businessName;
    @Column(name="town")
    private String town;
    @Column(name="street_name")
    private String streetName;
    @Column(name="building_name")
    private String buildingName;
    @Column(name="room_number")
    private String roomNumber;
}
