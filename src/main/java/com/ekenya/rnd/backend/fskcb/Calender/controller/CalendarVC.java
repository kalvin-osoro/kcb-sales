package com.ekenya.rnd.backend.fskcb.Calender.controller;

import com.ekenya.rnd.backend.fskcb.Calender.model.AppointmentByDSRRequest;
import com.ekenya.rnd.backend.fskcb.Calender.model.AssignMembers;
import com.ekenya.rnd.backend.fskcb.Calender.model.CalendarRequest;
import com.ekenya.rnd.backend.fskcb.Calender.service.CalendarService;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAppointmentDateRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBCampaignsRequest;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class CalendarVC {

    @Autowired
    CalendarService calendarService;

    @PostMapping("/create-appointment")
    public ResponseEntity<?> createCalendar(@RequestBody CalendarRequest model) {
        boolean success =calendarService.createCalendar(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    //assign members to appointment
    @PostMapping("/assign-members")
    public ResponseEntity<?> assignMembers(@RequestBody AssignMembers model) {
        boolean success =calendarService.assignMembersToAppointment(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

                return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
            }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
            }
        }
        //get for owner dsr
        @PostMapping(value = "/get-appointment-by-dsr-id-and-date")
        public ResponseEntity getDSRApointment(@RequestBody AppointmentByDSRRequest model){
            List<?> appointments=calendarService.getCustomerAppointmentByDSRIdAndDate(model);
            boolean success=appointments!=null;
            //Response
            ObjectMapper objectMapper = new ObjectMapper();
            if(success){
                //Object
                ArrayNode node = objectMapper.createArrayNode();
                node.addAll((List)appointments);

                return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
            }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
            }
        }
}
