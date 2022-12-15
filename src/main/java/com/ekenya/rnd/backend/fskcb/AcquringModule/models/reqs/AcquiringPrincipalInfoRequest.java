package com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AcquiringPrincipalInfoRequest {
    private String nameOfDirectorOrPrincipalOrPartner;
    private String directorOrPrincipalOrPartnerPhoneNumber;
    private String directorOrPrincipalOrPartnerEmail;
}
