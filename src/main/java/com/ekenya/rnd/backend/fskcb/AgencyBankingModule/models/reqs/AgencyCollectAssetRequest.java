package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.BusinessUnit;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AgencyCollectAssetRequest {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AgencyBankingQuestionnareQuestionRequest {
        private String question;
        private  String questionnaireDescription;
        private BusinessUnit businessUnit;
        private QuestionType questionType;
    }
}
