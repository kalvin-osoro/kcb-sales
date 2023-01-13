package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AssetCondition;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.AssetType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DFSVoomaAddAssetRequest {
    private org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
    private Long deviceId;
    private String serialNumber;
    private AssetCondition assetCondition;
    private AssetType assetType;
    private Long assetNumber;
    private String lastServiceDate;
    private String visitDate;
    private String dsrId;


}
