package com.ekenya.rnd.backend.fskcb.AcquringModule.models.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class QnResWrapper {
    private String question;
    private String response;
    private String questionId;
}
