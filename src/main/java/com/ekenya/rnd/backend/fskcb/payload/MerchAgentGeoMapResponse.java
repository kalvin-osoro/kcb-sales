package com.ekenya.rnd.backend.fskcb.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchAgentGeoMapResponse {
    private String latitude;
    private String longitude;
    private String businessName;
    private String mobileNumber;
    private String countyName;
    private String town;
    private String streetName;
    private String buildingName;
    private String roomNumber;
}
