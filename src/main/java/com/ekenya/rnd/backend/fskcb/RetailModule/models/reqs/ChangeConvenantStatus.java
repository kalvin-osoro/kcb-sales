package com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs;

import com.ekenya.rnd.backend.utils.ConcessionTrackingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeConvenantStatus {
    private Long id;
    private ConcessionTrackingStatus status;
}
