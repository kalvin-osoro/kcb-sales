package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PSCustomerVisitQuestionnaireRequest {
    private Long visitId;
    private Long questionId;
}
