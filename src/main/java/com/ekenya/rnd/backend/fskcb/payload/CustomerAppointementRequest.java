package com.ekenya.rnd.backend.fskcb.payload;

import com.ekenya.rnd.backend.fskcb.utils.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerAppointementRequest {
    private Logger log = LoggerFactory.getLogger(getClass());
    private Long id;
   @NotEmpty(message = "customer appointmentDate is required")
    private String appointmentDate;
    @NotEmpty(message = "customer appointmentDuration is required")
    private String appointmentDuration;
    @NotEmpty(message = "customer Name is required")
    private String accountName;
    @NotEmpty(message = "customer customerType is required")
    private String customerType;
    @NotEmpty(message = "customer phoneNumber is required")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    @NotEmpty(message = "reason for appointment  is required")
    @Lob
    @Column(name="reson_for_appointment", length=512)
    private String reasonsForAppointment;
    @NotBlank(message = "Created by is mandatory")
    private Long createdBy;
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
