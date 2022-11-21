package com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreasuryAddQuestionnaireRequest {
    private Long questionnaireId;
    private String question;
    private  String questionnaireDescription;
    private Date createdOn;
}
