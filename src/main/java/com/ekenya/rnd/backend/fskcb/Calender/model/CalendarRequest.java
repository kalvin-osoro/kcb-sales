package com.ekenya.rnd.backend.fskcb.Calender.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarRequest {
    private String owner;
    private String ownerPhone;
    private String time;
    private String customerPhoneNumber;
    private String venue;
    private String customerName;
    private String reason;
    private Long dsrId;
    private Boolean isOnline=false;
    private String dateOfEvent;
    private String period;
    private String ownerEmail;
    private String salesCode;
    private String profileCode;

}
