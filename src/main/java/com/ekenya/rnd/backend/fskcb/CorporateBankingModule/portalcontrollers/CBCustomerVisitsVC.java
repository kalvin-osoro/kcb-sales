package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.ekenya.rnd.backend.fskcb.service.CustomerAppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CBCustomerVisitsVC {
    //
   private final CustomerAppointmentService customerAppointmentService;

    public CBCustomerVisitsVC(CustomerAppointmentService customerAppointmentService) {
        this.customerAppointmentService = customerAppointmentService;
    }

    @RequestMapping(value = "/cb-create-customer-appointment", method = RequestMethod.POST)
    public ResponseEntity<?> createCustomerAppointment(@RequestParam CustomerAppointementRequest customerAppointmentRequest)
    {
        return customerAppointmentService.createCustomerAppointment(customerAppointmentRequest);
    }
    //update customer appointment
    @RequestMapping(value = "/cb-update-customer-appointment", method = RequestMethod.POST)
    public ResponseEntity<?> updateCustomerAppointment(@RequestParam CustomerAppointementRequest customerAppointmentRequest)
    {
        return customerAppointmentService.editCustomerAppointment(customerAppointmentRequest);
    }
    //get customer appointment by dsrId
    @RequestMapping(value = "/cb-get-customer-appointment", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerAppointment(@RequestParam Long dsrId)
    {
        return customerAppointmentService.getAllAppointmentByDsrId(dsrId);
    }

    //delete customer appointment
    @RequestMapping(value = "/cb-delete-customer-appointment", method = RequestMethod.GET)
    public ResponseEntity<?> deleteCustomerAppointment(@RequestParam Long appointmentId)
    {
        return customerAppointmentService.deleteCustomerAppointment(appointmentId);
    }
}
