package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites.RetailLeadEntity;
import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.repository.RetailLeadRepository;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetailPortalService implements IRetailPortalService {
    private final RetailLeadRepository retailLeadRepository;

    @Override
    public boolean assignLead(RetailAssignLeadRequest model) {
        try {
            RetailLeadEntity retailLeadEntity = retailLeadRepository.findById(model.getLeadId()).orElse(null);
            retailLeadEntity.setDsrId(model.getDsrId());
            //set start date from input
            retailLeadEntity.setStartDate(model.getStartDate());
            retailLeadEntity.setEndDate(model.getEndDate());
            //save
            retailLeadRepository.save(retailLeadEntity);
            //update is assigned to true
            retailLeadEntity.setAssigned(true);
            return true;


        } catch (Exception e) {
            log.error("Error assigning lead to dsr", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllLeads() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (RetailLeadEntity retailLeadEntity : retailLeadRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", retailLeadEntity.getId());
                objectNode.put("customerId", retailLeadEntity.getCustomerId());
                objectNode.put("businessUnit", retailLeadEntity.getBusinessUnit());
                objectNode.put("leadStatus", retailLeadEntity.getLeadStatus().ordinal());
                objectNode.put("topic", retailLeadEntity.getTopic());
                objectNode.put("priority", retailLeadEntity.getPriority().ordinal());
                objectNode.put("dsrId", retailLeadEntity.getDsrId());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all leads", e);
        }
        return null;
    }
}
