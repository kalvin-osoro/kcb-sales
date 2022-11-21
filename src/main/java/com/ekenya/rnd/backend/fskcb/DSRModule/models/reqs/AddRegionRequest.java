package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddRegionRequest {

    @NotNull
    private String name;

    private String code;
    @NotNull
    private String bounds;
}
