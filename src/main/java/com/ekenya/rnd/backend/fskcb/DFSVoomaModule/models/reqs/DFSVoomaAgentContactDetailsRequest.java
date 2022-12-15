package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DFSVoomaAgentContactDetailsRequest {
    private String contactPersonName;
    private String contactPersonPhoneNumber;
    private String contactPersonEmailAddress;
    private String contactPersonIdNumber;
    private String contactPersonIdType;
    private String financialContactPersonName;
    private String financialContactPersonPhoneNumber;
    private String financialContactPersonEmailAddress;
    private String financialContactPersonIdNumber;
    private String financialContactPersonIdType;
    //administrative contact
    private String administrativeContactPersonName;
    private String administrativeContactPersonPhoneNumber;
    private String administrativeContactPersonEmailAddress;
    private String administrativeContactPersonIdNumber;
    private String administrativeContactPersonIdType;
}
