package com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcquiringNearbyCustomersRequest {

    private double lat;
    private double lng;
    private double zoom;
}
