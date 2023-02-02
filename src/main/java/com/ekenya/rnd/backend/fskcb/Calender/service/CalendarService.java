package com.ekenya.rnd.backend.fskcb.Calender.service;

import com.ekenya.rnd.backend.fskcb.Calender.model.AppointmentByDSRRequest;
import com.ekenya.rnd.backend.fskcb.Calender.model.AssignMembers;
import com.ekenya.rnd.backend.fskcb.Calender.model.CalendarRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAppointmentDateRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface CalendarService {
    boolean createCalendar(CalendarRequest model);

    boolean assignMembersToAppointment(AssignMembers model);

    List<ObjectNode> getCustomerAppointmentByDSRIdAndDate(AppointmentByDSRRequest model);
}
