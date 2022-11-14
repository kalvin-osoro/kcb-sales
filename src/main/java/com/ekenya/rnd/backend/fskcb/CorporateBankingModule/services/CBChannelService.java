package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.entity.CustomerAppointments;
import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.ekenya.rnd.backend.fskcb.repository.CustomerAppointmentsRepository;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import com.ekenya.rnd.backend.fskcb.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor

@Service
public class CBChannelService implements IBChannelService {
    @Autowired
    private CustomerAppointmentsRepository customerAppointmentsRepository;


    @Override
    public boolean createCustomerAppointment(CustomerAppointementRequest customerAppointementRequest) {
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

//            responseObject.put("status", "success");
//            responseObject.put("message", "Business type "
//                    +customerAppointementRequest.getAccountName()+" successfully created");
//            responseObject.put("data", responseParams);
            return true;
        }catch (Exception e){
//            responseObject.put("status", "failed");
//            responseObject.put("message", e.getMessage());
//            return ResponseEntity.ok().body(responseObject);
        }
        return false;
    }

    @Override
    public List<?> getAllCustomerAppointment() {
        HashMap<String,Object> responseObject = new HashMap<>();
        HashMap<String,Object> responseParams= new HashMap<>();

        try {
            List<CustomerAppointments> customerAppointmentsList= customerAppointmentsRepository.findCustomerAppointmentsByStatus(Status.ACTIVE);
            if (customerAppointmentsList.isEmpty()){
//                responseObject.put("status","failed");
//                responseObject.put("message","No appointments found");
//                responseObject.put("data",responseParams);
                return new ArrayList<>();
            }
//            responseObject.put("status","success");
//            responseObject.put("message","Appointments found");
//            responseObject.put("data",customerAppointmentsList);
            return customerAppointmentsList;
        } catch (Exception e) {
//            responseObject.put("status","failed");
//            responseObject.put("message","No appointments found");
//            responseObject.put("data",responseParams);
//            return ResponseEntity.ok(responseObject);
        }

        return null;

    }



    @Override
    public boolean editCustomerAppointment(CustomerAppointementRequest customerAppointementRequest) {
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
            //return getResponseEntity(customerAppointementRequest, responseObject, responseParams, customerAppointments);

            return true;
        } catch (Exception e) {
//            responseObject.put("status","failed");
//            responseObject.put("message","Appointment for " + customerAppointementRequest.getAccountName() + " has not been created");
//            responseObject.put("data",responseParams);
//            return ResponseEntity.ok(responseObject);
        }

        return false;
    }



    @Override
    public boolean deleteCustomerAppointment(long id) {
        HashMap<String,Object> responseObject = new HashMap<>();
        HashMap<String,Object> responseParams= new HashMap<>();
        try {
            Optional<CustomerAppointments> customerAppointmentsOptional = customerAppointmentsRepository.findById(id);
            CustomerAppointments customerAppointments = customerAppointmentsOptional.get();
            customerAppointments.setStatus(Status.DELETED);
            customerAppointmentsRepository.save(customerAppointments);
//            responseObject.put("status","success");
//            responseObject.put("message","Appointment for " + customerAppointments.getAccountName() + " has been deleted");
//            responseObject.put("data",responseParams);
//            return ResponseEntity.ok(responseObject);
            return true;
        } catch (Exception e) {
//            responseObject.put("status","failed");
//            responseObject.put("message","Appointment for " + id + " has not been deleted");
//            responseObject.put("data",responseParams);
//            return ResponseEntity.ok(responseObject);
        }
        return false;
    }

    @Override
    public List<?> getAllAppointmentByDsrId(long dsrId) {
        HashMap<String,Object> responseObject = new HashMap<>();
        HashMap<String,Object> responseParams= new HashMap<>();
        try {
            List<CustomerAppointments> customerAppointmentsList = customerAppointmentsRepository.findCustomerAppointmentsByCreatedBy(dsrId);
            if (customerAppointmentsList.isEmpty()){
//                responseObject.put("status","failed");
//                responseObject.put("message","No appointments found");
//                responseObject.put("data",responseParams);
//                return ResponseEntity.ok(responseObject);
                return new ArrayList<>();
            }
//            responseObject.put("status","success");
//            responseObject.put("message","Appointments found");
//            responseObject.put("data",customerAppointmentsList);
//            return ResponseEntity.ok(responseObject);
            return customerAppointmentsList;
        } catch (Exception e) {
//            responseObject.put("status","failed");
//            responseObject.put("message","No appointments found");
//            responseObject.put("data",responseParams);
//            return ResponseEntity.ok(responseObject);
        }

        return null;

    }
}
