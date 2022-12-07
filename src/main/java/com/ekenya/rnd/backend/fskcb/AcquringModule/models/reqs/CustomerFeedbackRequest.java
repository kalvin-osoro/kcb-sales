package com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFeedbackRequest {
    private String describeTheService;
    private String whatWouldYouChange;
    private String whyWouldYouChange;
}
