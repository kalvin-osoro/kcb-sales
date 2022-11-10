package com.ekenya.rnd.backend.fskcb.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchartDetailResponse {
    private Long id;
    private String merchAgentAccountTypeName;
    private String businessName;
    private String phoneNo;
    private String email;
    private String businessTypeName;
    private String natureBusiness;
    private String liquidationType;
    private double liquidationRate;
    private String branchCode;
    private String branchName;
    private String bankCode;
    private String bankName;
    private String accountName;
    private String accountNumber;
    private Long countyCode;
    private String countyName;
    private String town;
    private String streetName;
    private String buildingName;
    private String roomNumber;

    List<Map<String,Object>> merchantKYCList;

    private String  latitude ;
    private String  longitude ;
    private String accountId;
    private Long createdBy;
    private Long updatedBy;

    private String merchantIDNumber;
    private String merchantSurname;
    private String merchantFirstName;
    private String merchantLastName;
    private String merchantGender;
    private String merchantDob;
    private String accountType;
}
