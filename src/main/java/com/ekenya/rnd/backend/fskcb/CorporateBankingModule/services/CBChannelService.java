package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBLeadEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories.ICBLeadsRepository;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.entity.CustomerAppointments;
import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
public class CBChannelService implements ICBChannelService {
    private final ICBLeadsRepository cbLeadsRepository;

    @Override
    public boolean createCBLead(CBAddLeadRequest model) {
        try {
            CBLeadEntity cbLeadEntity = new CBLeadEntity();
            cbLeadEntity.setCustomerName(model.getCustomerName());
            cbLeadEntity.setBusinessUnit(model.getBusinessUnit());
            cbLeadEntity.setPriority(model.getPriority());
            cbLeadEntity.setCustomerAccountNumber(model.getCustomerAccountNumber());
            cbLeadEntity.setTopic(model.getTopic());
            cbLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllLeadsByDsrId(Long dsrId) {
        return null;
    }

//    @Override
//    public List<?> getAllCustomerAppointment() {
//        return null;
//    }
//
//    @Override
//    public boolean createCustomerAppointment(CustomerAppointementRequest customerAppointementRequest) {
//        return false;
//    }
//
//    @Override
//    public boolean editCustomerAppointment(CustomerAppointementRequest customerAppointementRequest) {
//        return false;
//    }
//
//    @Override
//    public boolean deleteCustomerAppointment(long id) {
//        return false;
//    }
//
//    @Override
//    public List<?> getAllAppointmentByDsrId(long dsrId) {
//        return null;
//    }
}
