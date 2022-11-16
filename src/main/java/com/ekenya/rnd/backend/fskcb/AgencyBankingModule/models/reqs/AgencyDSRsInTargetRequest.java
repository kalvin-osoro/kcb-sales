package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgencyDSRsInTargetRequest {
    private String targetId;
    private String[] dsrId;
    private Integer setTarget;
    private Integer achievedTarget;
    private String dsrName;
    private String targetName;
    private Date createdOn;

}
