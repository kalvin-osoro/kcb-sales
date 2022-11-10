package com.ekenya.rnd.backend.fskcb.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class OnboardMerchantRequest {
    private org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
    private Long userAccountTypeId;
    private Long merchantAccountTypeId;
    private String businessName;
    private String mobileNumber;
    private String email;
    private Long businessTpeId;
    private String businessNature;
    private long liquidationTypeId;
    private double liquidationRate;
    private String bankCode;
    private String branchCode;
    private String accountName;
    private String accountNumber;
    private long countyCode;
    /*private long constituencyCode;
    private long wardCode;*/
    private String townName;
    private String streetName;
    private String buldingName;
    private String roomNo;
    /*private String adminFullName;
    private String adminUserName;
    private String adminPhoneNo;
    private String adminEmail;*/
	/*private String termsAndConditionDoc;
	private String kfdPhoto;
	private String customerPhoto;
	private String signatureDoc;
	private String businessPermitDoc;*/
    private String  latitude ;
    private String  longitude ;
    private String merchantIDNumber;
    private String merchantSurname;
    private String merchantFirstName;
    private String merchantLastName;
    private String merchantGender;
    private String merchantDob;


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


