package com.deltacode.kcb.payload;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireQuestionRequest {
    private long userAccountType;
    private String question;
    private String questionDescription;
    private long questionType;
    private String status;
    private String choices;

}
