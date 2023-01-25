package com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.SearchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CRMRequest {
    private String account;
}
