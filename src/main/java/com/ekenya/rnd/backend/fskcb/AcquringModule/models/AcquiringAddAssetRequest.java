package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

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
public class AcquiringAddAssetRequest {
    private org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
    private Long deviceId;
    private String serialNumber;
    private String condition;
    private String lastServiceDate;
    private String visitDate;
    private String dsrId;

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            log.info("Error is "+ex.getMessage());
        }
        return "{}";
    }
}
