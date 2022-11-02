package com.deltacode.kcb.CorporateBankingModule.controllers;

import com.deltacode.kcb.payload.CustomerAppointementRequest;
import com.deltacode.kcb.service.CustomerAppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/corporate-customer-visits")
public class CorporateBankingCustomerVisitsController {
    //TODO: add the customer Visits service
   private final CustomerAppointmentService customerAppointmentService;

    public CorporateBankingCustomerVisitsController(CustomerAppointmentService customerAppointmentService) {
        this.customerAppointmentService = customerAppointmentService;
    }

    @RequestMapping(value = "/create-corporate-customer-appointment", method = RequestMethod.POST)
    public ResponseEntity<?> createCustomerAppointment(@RequestParam CustomerAppointementRequest customerAppointmentRequest)
    {
        return customerAppointmentService.createCustomerAppointment(customerAppointmentRequest);
    }
    //update customer appointment
    @RequestMapping(value = "/update-corporate-customer-appointment", method = RequestMethod.POST)
    public ResponseEntity<?> updateCustomerAppointment(@RequestParam CustomerAppointementRequest customerAppointmentRequest)
    {
        return customerAppointmentService.editCustomerAppointment(customerAppointmentRequest);
    }
    //get customer appointment by dsrId
    @RequestMapping(value = "/get-corporate-customer-appointment", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerAppointment(@RequestParam Long dsrId)
    {
        return customerAppointmentService.getAllAppointmentByDsrId(dsrId);
    }

    //delete customer appointment
    @RequestMapping(value = "/delete-corporate-customer-appointment", method = RequestMethod.GET)
    public ResponseEntity<?> deleteCustomerAppointment(@RequestParam Long appointmentId)
    {
        return customerAppointmentService.deleteCustomerAppointment(appointmentId);
    }
}
