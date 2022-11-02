package com.deltacode.kcb.payload;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetManagementRequest {
    private org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
    @NotNull(message = "Asset ID cannot be null")
    private Long assetId;
    @NotNull(message = "Account type ID cannot be null")
    private Long userAccountType;
    @NotNull(message = "National ID cannot be null")
    private String serialNumber;
    @NotNull(message = "Account no cannot be null")
    private String accountNo;
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
