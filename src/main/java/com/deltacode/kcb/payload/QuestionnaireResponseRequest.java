package com.deltacode.kcb.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireResponseRequest {
    private long questionnaireQuestion;
    private String questionResponse;
}
