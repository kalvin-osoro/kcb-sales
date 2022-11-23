package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcquiringAssignAssetRequest {

    private Long assetId;
    private Long agentId;
}
