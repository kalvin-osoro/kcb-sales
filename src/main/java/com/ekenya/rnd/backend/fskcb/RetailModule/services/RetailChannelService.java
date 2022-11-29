package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites.RetailLeadEntity;
import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.repository.RetailLeadRepository;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddLeadRequest;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetailChannelService implements IRetailChannelService {
    @Autowired
    RetailLeadRepository retailLeadRepository;

    @Override
    public boolean createLead(RetailAddLeadRequest model) {
        try {
            RetailLeadEntity retailLeadEntity = new RetailLeadEntity();
            retailLeadEntity.setCustomerName(model.getCustomerName());
            retailLeadEntity.setBusinessUnit(model.getBusinessUnit());
            retailLeadEntity.setPriority(model.getPriority());
            retailLeadEntity.setCustomerAccountNumber(model.getCustomerAccountNumber());
            retailLeadEntity.setTopic(model.getTopic());
            retailLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            retailLeadRepository.save(retailLeadEntity);

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }

    @Override
    public ObjectNode loadDSRAnalytics(String staffNo) {
        return null;
    }
}
