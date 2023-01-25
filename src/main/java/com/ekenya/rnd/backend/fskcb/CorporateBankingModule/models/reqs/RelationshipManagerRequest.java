package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipManagerRequest {
//    private String rmName;
    private Long rmId;
    private String rmPhoneNumber;
    private String rmName;

    private Long appointmentId;


}
