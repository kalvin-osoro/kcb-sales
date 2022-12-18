package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAppointmentDateRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAppointmentRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAppointmentUpdateRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class CBCustomerAppointmentVC {
    @Autowired
    private ICBChannelService channelService;

    @PostMapping(value = "/cb-create-customer-appointmentV1")
    public ResponseEntity createCustomerAppointment(@RequestBody CBAppointmentRequest model){
        boolean success= channelService.createCustomerAppointment(model);
        if(success){
            return ResponseEntity.ok("Request Processed Successfully");
        }else{
            return ResponseEntity.ok("Request could NOT be processed. Please try again later");
        }

    }
    //find by dsr id and date
    @PostMapping(value = "/cb-get-customer-appointment-by-dsr-id-and-date")
    public ResponseEntity getCustomerAppointmentByDSRIdAndDate(@RequestBody CBAppointmentDateRequest model){
        List<?> appointments=channelService.getCustomerAppointmentByDSRIdAndDate(model);
        boolean success=appointments!=null;
        if(success){
            return ResponseEntity.ok(appointments);
        }else{
            return ResponseEntity.ok("Request could NOT be processed. Please try again later");
        }

}
//update appointment
    @PostMapping(value = "/cb-update-customer-appointment")
    public ResponseEntity updateCustomerAppointment(@RequestBody CBAppointmentUpdateRequest model){
        boolean success= channelService.updateCustomerAppointment(model);
        if(success){
            return ResponseEntity.ok("Request Processed Successfully");
        }else{
            return ResponseEntity.ok("Request could NOT be processed. Please try again later");
        }


    }
}
