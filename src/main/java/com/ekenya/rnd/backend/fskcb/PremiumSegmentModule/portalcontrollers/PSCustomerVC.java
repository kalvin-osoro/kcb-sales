package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.ekenya.rnd.backend.fskcb.service.CustomerAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/premium-customer-visit")
public class PSCustomerVC {

    //TODO: add the customer Visits service
    @Autowired
    CustomerAppointmentService customerAppointmentService;
    @RequestMapping(value = "/create-appointment", method = RequestMethod.POST)
    public ResponseEntity<?> createCustomerAppointment(@RequestParam CustomerAppointementRequest customerAppointmentRequest)
    {
        return customerAppointmentService.createCustomerAppointment(customerAppointmentRequest);
    }
    //update customer appointment
    @RequestMapping(value = "/update-appointment", method = RequestMethod.POST)
    public ResponseEntity<?> updateCustomerAppointment(@RequestParam CustomerAppointementRequest customerAppointmentRequest)
    {
        return customerAppointmentService.editCustomerAppointment(customerAppointmentRequest);
    }
    //get customer appointment by dsrId
    @RequestMapping(value = "/get-appointment", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerAppointment(@RequestParam Long dsrId)
    {
        return customerAppointmentService.getAllAppointmentByDsrId(dsrId);
    }

    //delete customer appointment
    @RequestMapping(value = "/delete-appointment", method = RequestMethod.GET)
    public ResponseEntity<?> deleteCustomerAppointment(@RequestParam Long appointmentId)
    {
        return customerAppointmentService.deleteCustomerAppointment(appointmentId);
    }
}
