package com.ekenya.rnd.backend.fskcb.RetailModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.ekenya.rnd.backend.fskcb.payload.GetCustomerRequest;
import com.ekenya.rnd.backend.fskcb.service.CustomerAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class RetailCustomerVC {
    @Autowired
    CustomerAppointmentService customerAppointmentService;
    @RequestMapping(value = "/retail-customer-visits", method = RequestMethod.POST)
    public ResponseEntity<?> getCustomerVisits(@RequestBody GetCustomerRequest getCustomerRequest) {
        return null;
    }
    @RequestMapping(value = "/create-customer-appointment", method = RequestMethod.POST)
    public ResponseEntity<?> createCustomerAppointment(@RequestParam CustomerAppointementRequest customerAppointmentRequest)
    {
        return customerAppointmentService.createCustomerAppointment(customerAppointmentRequest);
    }
    //update customer appointment
    @RequestMapping(value = "/update-customer-appointment", method = RequestMethod.POST)
    public ResponseEntity<?> updateCustomerAppointment(@RequestParam CustomerAppointementRequest customerAppointmentRequest)
    {
        return customerAppointmentService.editCustomerAppointment(customerAppointmentRequest);
    }
    //get customer appointment by dsrId
    @RequestMapping(value = "/get-customer-appointment", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerAppointment(@RequestParam Long dsrId)
    {
        return customerAppointmentService.getAllAppointmentByDsrId(dsrId);
    }

    //delete customer appointment
    @RequestMapping(value = "/delete-customer-appointment", method = RequestMethod.GET)
    public ResponseEntity<?> deleteCustomerAppointment(@RequestParam Long appointmentId)
    {
        return customerAppointmentService.deleteCustomerAppointment(appointmentId);
    }
}
