package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.JsonLatLng;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateRegionRequest {

    private long id;

    private String name;

    private String code;
    @NotNull
    private List<JsonLatLng> bounds = new ArrayList<>();
}
