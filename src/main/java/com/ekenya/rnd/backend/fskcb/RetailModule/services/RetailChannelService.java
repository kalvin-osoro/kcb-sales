package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites.RetailLeadEntity;
import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.repository.RetailLeadRepository;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailGetDSRLead;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRLeads;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryUpdateLeadRequest;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RetailChannelService implements IRetailChannelService {

    //create a random number generator
    @Autowired
    RetailLeadRepository retailLeadRepository;



    @Override
    public ObjectNode loadDSRAnalytics(String staffNo) {
        return null;
    }


    @Override
    public boolean attemptCreateMicroLead(TreasuryAddLeadRequest model) {
        try {
            RetailLeadEntity retailLeadEntity = new RetailLeadEntity();
            retailLeadEntity.setCustomerName(model.getCustomerName());
            retailLeadEntity.setBusinessUnit(model.getBusinessUnit());
            retailLeadEntity.setEmail(model.getEmail());
            retailLeadEntity.setPhoneNumber(model.getPhoneNumber());
            retailLeadEntity.setProduct(model.getProduct());
            retailLeadEntity.setPriority(model.getPriority());
            retailLeadEntity.setDsrId(model.getDsrId());
            retailLeadEntity.setDsrName(model.getDsrName());
            retailLeadEntity.setCustomerAccountNumber(model.getCustomerAccountNumber());
            retailLeadEntity.setTopic(model.getTopic());
            retailLeadEntity.setLeadStatus(LeadStatus.OPEN);
            retailLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            retailLeadRepository.save(retailLeadEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadDSRLead(TreasuryGetDSRLeads model) {
        try {
            if (model==null){
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (RetailLeadEntity retailLeadEntity : retailLeadRepository.findAllByDsrIdAndAssigned(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", retailLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", retailLeadEntity.getPriority().toString());
                node.put("businessUnit", retailLeadEntity.getBusinessUnit());
                node.put("leadId", retailLeadEntity.getId());
                node.put("leadStatus", retailLeadEntity.getLeadStatus().ordinal());
                node.put("createdOn", retailLeadEntity.getCreatedOn().getTime());
                list.add(node);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while loading assigned leads", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> loadAssignedDSRLead(TreasuryGetDSRLeads model) {
        try {
            if (model==null){
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (RetailLeadEntity retailLeadEntity : retailLeadRepository.findAllAssignedLeadByDSRId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", retailLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", retailLeadEntity.getPriority().toString());
                node.put("businessUnit", retailLeadEntity.getBusinessUnit());
                node.put("leadId", retailLeadEntity.getId());
                list.add(node);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while loading assigned leads", e);
        }
        return null;
    }

    @Override
    public boolean attemptUpdateLead(TreasuryUpdateLeadRequest model) {
        try {
            if (model==null){
                return false;
            }
            RetailLeadEntity retailLeadEntity = retailLeadRepository.findById(model.getLeadId()).orElse(null);
            retailLeadEntity.setOutcomeOfTheVisit(model.getOutcomeOfTheVisit());
            retailLeadEntity.setLeadStatus(model.getLeadStatus());
            retailLeadRepository.save(retailLeadEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while updating lead", e);
        }
        return false;
    }

    @Override
    public ArrayNode loadDSRSummary() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode list = mapper.createArrayNode();
            ObjectNode node = mapper.createObjectNode();
            node.put("kpi","Loans");
            node.put("target", 0);
            node.put("actual", 0);
            node.put("percentage", 0);
            list.add(node);
            //kpi accounts
            ObjectNode node1 = mapper.createObjectNode();
            node1.put("kpi","Accounts");
            node1.put("target", 0);
            node1.put("actual", 0);
            node1.put("percentage", 0);
            list.add(node1);

            //vooma
            ObjectNode node2 = mapper.createObjectNode();
            node2.put("kpi","Vooma");
            node2.put("target", 0);
            node2.put("actual", 0);
            node2.put("percentage", 0);
            list.add(node2);
            //insurance
            ObjectNode node3 = mapper.createObjectNode();
            node3.put("kpi","Insurance");
            node3.put("target", 0);
            node3.put("actual", 0);
            node3.put("percentage", 0);
            list.add(node3);
            //deposit
            ObjectNode node4 = mapper.createObjectNode();
            node4.put("kpi","Deposit");
            node4.put("target", 0);
            node4.put("actual", 0);
            node4.put("percentage", 0);
            list.add(node4);
            //credit card
            ObjectNode node5 = mapper.createObjectNode();
            node5.put("kpi","Credit Card");
            node5.put("target", 0);
            node5.put("actual", 0);
            node5.put("percentage", 0);
            list.add(node5);

            ObjectNode node6 =mapper.createObjectNode();
            node5.put("retainer", 0);
            node5.put("commission", 0);
            list.add(node6);


            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading summary", e);
        }
        return null;
    }
}
