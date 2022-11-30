package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.JsonLatLng;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddRegionRequest {

    @NotNull
    private String name;

    private String code;
    @NotNull
    private List<JsonLatLng> bounds = new ArrayList<>();
}
