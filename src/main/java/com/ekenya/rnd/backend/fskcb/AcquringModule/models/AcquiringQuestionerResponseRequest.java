package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcquiringQuestionerResponseRequest {
    private Long visitId;
    private Long questionId;
    private  String response;
}
