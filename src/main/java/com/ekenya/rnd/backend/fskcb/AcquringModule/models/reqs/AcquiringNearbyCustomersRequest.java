package com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcquiringNearbyCustomersRequest {

    private double latitude;
    private double longitude;
//    private double zoom;
    private double radius;
}
