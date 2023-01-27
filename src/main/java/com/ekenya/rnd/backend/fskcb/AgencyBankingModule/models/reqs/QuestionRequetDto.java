package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.BusinessUnit;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequetDto {
    private BusinessUnit businessUnit;
    private QuestionType questionType;
}
