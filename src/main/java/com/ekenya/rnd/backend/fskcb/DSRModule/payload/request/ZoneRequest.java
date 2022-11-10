package com.ekenya.rnd.backend.fskcb.DSRModule.payload.request;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneRequest {
    private Long id;
    private String zoneName;
    private String status;
    private List<JsonObject> coordinatesList;
}
