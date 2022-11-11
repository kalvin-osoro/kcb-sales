package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AquiringTargetType;

public class AcquiringAddTargetRequest {

    private String name;
    private String desc;
    private String startDate;
    private String endDate;
    private AquiringTargetType targetType;
    private double tragetValue;
}
