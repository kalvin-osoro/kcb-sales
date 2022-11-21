package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import lombok.Data;

@Data
public class UpdateRegionRequest {

    private long id;

    private String name;

    private String code;
}
