package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBAppointmentUpdateRequest {
    private Long appointmentId;
    private String appointmentDate;
    private String appointmentTime;
}