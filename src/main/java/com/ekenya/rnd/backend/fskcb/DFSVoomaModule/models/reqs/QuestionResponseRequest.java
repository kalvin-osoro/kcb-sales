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
public class QuestionResponseRequest {
//    private  String response;
    private String customerName;
    private Long questionnaireId;
    private String nationalId;
//    private String comment;
    private String accountNo;
//    private Long questionId;

    private List<QuestionsAndResponses> questionsAndResponses;
}
