package com.ekenya.rnd.backend.fskcb.Calender.portalController;

import com.ekenya.rnd.backend.fskcb.Calender.model.GetAppointmentDto;
import com.ekenya.rnd.backend.fskcb.Calender.service.CalendarService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")

public class CalendarVC {
    private final CalendarService calendarService;


    public CalendarVC(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @PostMapping("/get-all-appointment")
    public ResponseEntity<?> getAllCampaigns(@RequestBody GetAppointmentDto model) {
        List<?> appointments = calendarService.getAllAppointments(model);
        boolean success = appointments != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)appointments);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
