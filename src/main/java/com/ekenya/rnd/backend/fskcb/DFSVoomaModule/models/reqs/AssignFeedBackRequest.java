package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignFeedBackRequest {
    private Long dsrId;
    private Long questionnaireId;
    private String duration;
    private String priority;
    private String escalationEmail;
    private String salesPersonName;

}
