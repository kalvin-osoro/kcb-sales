package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import lombok.*;

import javax.persistence.Lob;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcquiringAddQuestionnaireRequest {
    private String question;
    private  String questionnaireDescription;
    private Date createdOn;
}
