package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites.RetailLeadEntity;
import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.repository.RetailLeadRepository;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailGetDSRLead;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<?> getAllLeads(RetailGetDSRLead model) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (RetailLeadEntity retailLeadEntity : retailLeadRepository.findAllByDsrId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerId", retailLeadEntity.getCustomerId());
                node.put("businessUnit", retailLeadEntity.getBusinessUnit());
                node.put("leadStatus", String.valueOf(retailLeadEntity.getLeadStatus()));
                node.put("topic", retailLeadEntity.getTopic());
                node.put("priority", retailLeadEntity.getPriority().ordinal());
                node.put("dsrId", retailLeadEntity.getDsrId());
                //add to list
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading Leads", e);
        }
        return null;
    }
}
