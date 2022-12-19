package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites.RetailLeadEntity;
import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.repository.RetailLeadRepository;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAssignLeadRequest;
import com.ekenya.rnd.backend.utils.Utility;
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
    private final IDSRAccountsRepository dsrAccountsRepository;


    @Override
    public boolean assignLead(TreasuryAssignLeadRequest model) {
        try {
            if (model == null) {
                return false;
            }
            DSRAccountEntity dsrAccountsEntity = dsrAccountsRepository.findById(model.getDsrId()).orElse(null);
            if (dsrAccountsEntity == null) {
                return false;
            }
            RetailLeadEntity retailLeadEntity = retailLeadRepository.findById(model.getLeadId()).orElse(null);
            if (retailLeadEntity == null) {
                return false;
            }
            retailLeadEntity.setAssigned(true);
            retailLeadEntity.setPriority(model.getPriority());
            //set dsrAccId from dsrAccountsEntity
            retailLeadEntity.setDsrAccountEntity(dsrAccountsEntity);
            retailLeadRepository.save(retailLeadEntity);
            return true;



        } catch (Exception e) {
            log.error("Error occurred while assigning lead", e);
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
                objectNode.put("leadStatus", retailLeadEntity.getLeadStatus().toString());
                objectNode.put("createdOn",retailLeadEntity.getCreatedOn().getTime());
                objectNode.put("product",retailLeadEntity.getProduct());
                objectNode.put("dsrName", Utility.getDsrNameFromDsrId(retailLeadEntity.getDsrId()));
                objectNode.put("priority", retailLeadEntity.getPriority().toString());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all leads", e);
        }
        return null;
    }
}
