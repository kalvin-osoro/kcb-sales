package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UQuestionnaireQuestionRequest {
    private long userAccountType;
    private String question;
    private String questionDescription;
    private long questionType;
    private Status status;
    private String choices;
}
