package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireRequest {
    private String questionnaireTitle;
    private String questionnaireType;
    private String questionnaireDesc;
    private List<QuestionRequest> questionRequests;
}
