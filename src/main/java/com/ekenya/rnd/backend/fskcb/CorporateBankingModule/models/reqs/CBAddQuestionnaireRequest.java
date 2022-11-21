package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBAddQuestionnaireRequest {
    private Long questionnaireId;
    private String question;
    private  String questionnaireDescription;
    private Date createdOn;
}
