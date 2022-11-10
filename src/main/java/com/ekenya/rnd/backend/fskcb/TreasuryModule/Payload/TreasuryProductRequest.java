package com.ekenya.rnd.backend.fskcb.TreasuryModule.Payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreasuryProductRequest {
    private org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
    private long productCode;
    private String productName;
    private boolean status;
    private String description;
    private String productCategory;
    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            log.info("Error is ",ex.getMessage());
        }
        return "{}";
    }
}
