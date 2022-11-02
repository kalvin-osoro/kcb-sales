package com.deltacode.kcb.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnboardCustomerRequest {
    private Logger log = LoggerFactory.getLogger(getClass());
    @NotEmpty(message = "User userAccountTypeId cannot be null")
    private long  userAccountTypeId;
    @NotEmpty(message = "User phoneNo cannot be null")
    private String phoneNo;
    @NotEmpty(message = "User personalAccountTypeId cannot be null")
    private long personalAccountTypeId;
    @NotEmpty(message = "User privateLongKCBBranchId cannot be null")
    private long privateLongKCBBranchId;
    @NotEmpty(message = "User idNumber cannot be null")
    private String idNumber;
    @NotEmpty(message = "User surname cannot be null")
    private String surname;
    @NotEmpty(message = "User firstName cannot be null")
    private String firstName;
    private String lastName;
    @NotEmpty(message = "User sysUserId cannot be null")
    private long sysUserId;

    @Future
    private Date dob;
    @Size(min = 1, max = 1, message
            = "gender be a character")
    private String gender;
    @Positive
    private double income;
    @NotEmpty
    private long employmentType;
    @NotEmpty
    private String accountOpeningPurpose;
    @NotEmpty
    private String companyYouWorkFor;
    @NotEmpty
    private String  latitude ;
    @NotEmpty
    private String  longitude ;

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
