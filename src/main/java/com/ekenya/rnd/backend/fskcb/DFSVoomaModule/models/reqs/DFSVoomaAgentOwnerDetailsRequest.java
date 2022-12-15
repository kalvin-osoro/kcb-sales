package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DFSVoomaAgentOwnerDetailsRequest {
    private String fullName;
    private String idNumber;
    private String idType;
    private String phoneNumber;
    private String emailAddress;
    private String dob;
}
