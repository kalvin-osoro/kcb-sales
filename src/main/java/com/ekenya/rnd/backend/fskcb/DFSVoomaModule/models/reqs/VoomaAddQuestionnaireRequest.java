package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoomaAddQuestionnaireRequest {
    private Long questionnaireId;
    private String question;
    private  String questionnaireDescription;
    private Date createdOn;
}
