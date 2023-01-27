package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectorRequest {
    private String sectorName;
    private String sectorDesc;
}
