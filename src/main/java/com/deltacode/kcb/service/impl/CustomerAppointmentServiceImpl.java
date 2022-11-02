package com.deltacode.kcb.service.impl;

import com.deltacode.kcb.entity.CustomerAppointments;
import com.deltacode.kcb.payload.CustomerAppointementRequest;
import com.deltacode.kcb.repository.CustomerAppointmentsRepository;
import com.deltacode.kcb.service.CustomerAppointmentService;
import com.deltacode.kcb.utils.Status;
import com.deltacode.kcb.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
@Service
public class CustomerAppointmentServiceImpl implements CustomerAppointmentService {
    @Autowired
     private CustomerAppointmentsRepository customerAppointmentsRepository;


    @Override
    public ResponseEntity<?> createCustomerAppointment(CustomerAppointementRequest customerAppointementRequest) {
        HashMap<String,Object> responseObject = new HashMap<>();
        HashMap<String,Object> responseParams= new HashMap<>();

        try {
            if(customerAppointementRequest==null) throw new RuntimeException("Bad request");

            CustomerAppointments customerAppointments = new CustomerAppointments();
            customerAppointments.setAppointmentDate(customerAppointementRequest.getAppointmentDate());
            customerAppointments.setAppointmentDate(customerAppointementRequest.getAppointmentDate());
            customerAppointments.setAppointmentDuration(customerAppointementRequest.getAppointmentDuration());
            customerAppointments.setCustomerType(customerAppointementRequest.getCustomerType());
            customerAppointments.setPhoneNumber(customerAppointementRequest.getPhoneNumber());
            customerAppointments.setResonsForAppointment(customerAppointementRequest.getReasonsForAppointment());


            customerAppointments.setCreatedBy(customerAppointementRequest.getCreatedBy());
            customerAppointments.setStatus(Status.ACTIVE);
            customerAppointments.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            customerAppointmentsRepository.save(customerAppointments);

            responseObject.put("status", "success");
            responseObject.put("message", "Business type "
                    +customerAppointementRequest.getAccountName()+" successfully created");
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            responseObject.put("status", "failed");
            responseObject.put("message", e.getMessage());
            return ResponseEntity.ok().body(responseObject);
        }
    }

    @Override
    public ResponseEntity<?> getAllCustomerAppointment() {
        HashMap<String,Object> responseObject = new HashMap<>();
        HashMap<String,Object> responseParams= new HashMap<>();

        try {
            List<CustomerAppointments> customerAppointmentsList= customerAppointmentsRepository.findCustomerAppointmentsByStatus(Status.ACTIVE);
            if (customerAppointmentsList.isEmpty()){
                responseObject.put("status","failed");
                responseObject.put("message","No appointments found");
                responseObject.put("data",responseParams);
                return ResponseEntity.ok(responseObject);
            }
            responseObject.put("status","success");
            responseObject.put("message","Appointments found");
            responseObject.put("data",customerAppointmentsList);
            return ResponseEntity.ok(responseObject);
        } catch (Exception e) {
            responseObject.put("status","failed");
            responseObject.put("message","No appointments found");
            responseObject.put("data",responseParams);
            return ResponseEntity.ok(responseObject);
        }

    }



    @Override
    public ResponseEntity<?> editCustomerAppointment(CustomerAppointementRequest customerAppointementRequest) {
        HashMap<String,Object> responseObject = new HashMap<>();
        HashMap<String,Object> responseParams= new HashMap<>();
        try {
            if (customerAppointementRequest==null) throw new RuntimeException("Bad request");
            if (!Utility.validateStatus((customerAppointementRequest.getStatus()))) throw new RuntimeException("Invalid status");

            Optional<CustomerAppointments> customerAppointmentsOptional = customerAppointmentsRepository.findById
                    (customerAppointementRequest.getId());
            CustomerAppointments customerAppointments = customerAppointmentsOptional.get();
            customerAppointments.setAppointmentDate(customerAppointementRequest.getAppointmentDate());
            customerAppointments.setStatus(customerAppointementRequest.getStatus());
            return getResponseEntity(customerAppointementRequest, responseObject, responseParams, customerAppointments);

        } catch (Exception e) {
            responseObject.put("status","failed");
            responseObject.put("message","Appointment for " + customerAppointementRequest.getAccountName() + " has not been created");
            responseObject.put("data",responseParams);
            return ResponseEntity.ok(responseObject);
        }
    }



    @Override
    public ResponseEntity<?> deleteCustomerAppointment(long id) {
        HashMap<String,Object> responseObject = new HashMap<>();
        HashMap<String,Object> responseParams= new HashMap<>();
        try {
            Optional<CustomerAppointments> customerAppointmentsOptional = customerAppointmentsRepository.findById(id);
            CustomerAppointments customerAppointments = customerAppointmentsOptional.get();
            customerAppointments.setStatus(Status.DELETED);
            customerAppointmentsRepository.save(customerAppointments);
            responseObject.put("status","success");
            responseObject.put("message","Appointment for " + customerAppointments.getAccountName() + " has been deleted");
            responseObject.put("data",responseParams);
            return ResponseEntity.ok(responseObject);
        } catch (Exception e) {
            responseObject.put("status","failed");
            responseObject.put("message","Appointment for " + id + " has not been deleted");
            responseObject.put("data",responseParams);
            return ResponseEntity.ok(responseObject);
        }
    }

    @Override
    public ResponseEntity<?> getAllAppointmentByDsrId(long dsrId) {
        HashMap<String,Object> responseObject = new HashMap<>();
        HashMap<String,Object> responseParams= new HashMap<>();
        try {
            List<CustomerAppointments> customerAppointmentsList = customerAppointmentsRepository.findCustomerAppointmentsByCreatedBy(dsrId);
            if (customerAppointmentsList.isEmpty()){
                responseObject.put("status","failed");
                responseObject.put("message","No appointments found");
                responseObject.put("data",responseParams);
                return ResponseEntity.ok(responseObject);
            }
            responseObject.put("status","success");
            responseObject.put("message","Appointments found");
            responseObject.put("data",customerAppointmentsList);
            return ResponseEntity.ok(responseObject);
        } catch (Exception e) {
            responseObject.put("status","failed");
            responseObject.put("message","No appointments found");
            responseObject.put("data",responseParams);
            return ResponseEntity.ok(responseObject);
        }

    }
    private ResponseEntity<?> getResponseEntity(CustomerAppointementRequest customerAppointementRequest,
                                                HashMap<String, Object> responseObject, HashMap<String, Object> responseParams,
                                                CustomerAppointments customerAppointments) throws Exception {
        customerAppointments.setCustomerType(customerAppointementRequest.getCustomerType());
        customerAppointments.setAppointmentDuration(customerAppointementRequest.getAppointmentDuration());
        customerAppointments.setPhoneNumber(customerAppointementRequest.getPhoneNumber());
        customerAppointments.setAccountName(customerAppointementRequest.getAccountName());
        customerAppointments.setResonsForAppointment(customerAppointementRequest.getReasonsForAppointment());
        customerAppointments.setCreatedBy(customerAppointementRequest.getCreatedBy());
        customerAppointments.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
        customerAppointmentsRepository.save(customerAppointments);
        responseObject.put("status","success");
        responseObject.put("message","Appointment for " + customerAppointementRequest.getAccountName() + " has been created");
        responseObject.put("data",responseParams);
        return ResponseEntity.ok(responseObject);
    }
}
