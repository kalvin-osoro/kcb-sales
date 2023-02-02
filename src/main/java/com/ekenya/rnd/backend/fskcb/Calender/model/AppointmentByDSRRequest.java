package com.ekenya.rnd.backend.fskcb.Calender.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentByDSRRequest {
    private String dateOfEvent;
    private Long dsrId;
}
