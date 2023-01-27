package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.QuestionnaireType;
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
    private QuestionnaireType questionnaireType;
    private String questionnaireDesc;
    private List<QuestionRequest> questionRequests;
}
