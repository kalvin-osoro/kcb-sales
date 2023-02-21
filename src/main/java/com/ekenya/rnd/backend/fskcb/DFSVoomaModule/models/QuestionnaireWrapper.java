package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.QuestionnaireType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireWrapper {
    private String profileCode;
    private QuestionnaireType questionnaireType;
}
