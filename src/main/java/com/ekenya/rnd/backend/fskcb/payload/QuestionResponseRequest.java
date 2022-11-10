package com.ekenya.rnd.backend.fskcb.payload;

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
