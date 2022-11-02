package com.deltacode.kcb.DSRModule.payload.response;

import com.deltacode.kcb.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneResponse {
    private Long id;
    private String zoneName;
    private Status status;
    private List<ZoneCoordinatesResponse> coordinatesList;
}
