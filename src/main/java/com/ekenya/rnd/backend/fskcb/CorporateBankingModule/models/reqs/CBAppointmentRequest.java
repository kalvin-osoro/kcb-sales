package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBAppointmentRequest {
    private String typeOfAppointment;
    private String appointmentDate;
    private String appointmentTime;
    private Long dsrId;
    private String duration;
    private String reasonForVisit;
    private String customerName;
    private String customerPhoneNumber;
    private Set<RelationshipManagerRequest> rm;
}
