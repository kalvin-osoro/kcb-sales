package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcquiringDSRsInTargetRequest {
    private String targetId;
    private String dsrId;
    private Integer setTarget;
    private Integer achievedTarget;
    private String dsrName;
    private String targetName;
    private Date createdOn;

}
