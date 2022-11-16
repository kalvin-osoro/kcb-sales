package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

public class AgencyCollectAssetRequest {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AgencyBankingQuestionnareQuestionRequest {
        private Long id;
        private String question;
        private  String questionnaireDescription;
        private Date createdOn;
    }
}
