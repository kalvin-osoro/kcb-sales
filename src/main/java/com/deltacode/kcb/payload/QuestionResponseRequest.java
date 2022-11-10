package com.deltacode.kcb.payload;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseRequest {
    private String accountId;
    List<QuestionnaireResponseRequest> listQuestionResponse;
}
