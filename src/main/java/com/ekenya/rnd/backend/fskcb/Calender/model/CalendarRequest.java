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
    private String venue;
    private String reason;
    private String dateOfEvent;
    private String period;
    private String phoneNumber;
    private String ownerEmail;
    private String salesCode;
    private String profileCode;

    private List<MemberRequest>memberRequests;
}
