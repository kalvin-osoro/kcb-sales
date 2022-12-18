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

    @Override
    public ArrayList<ObjectNode> loadDSRSummary() {
        try {
            ArrayList<ObjectNode>list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode deposits = mapper.createObjectNode();
            //hardcoded for now waiting for the APIs
            deposits.put("target", 1000);
            deposits.put("actual", 500);
            deposits.put("percentage", (deposits.get("actual").asInt() / deposits.get("target").asInt()) * 100);
            list.add(deposits);
            ObjectNode loans = mapper.createObjectNode();
            loans.put("target", 1000);
            loans.put("actual", 500);
            loans.put("percentage", (loans.get("actual").asInt() / loans.get("target").asInt()) * 100);
            list.add(loans);
            ObjectNode insurance = mapper.createObjectNode();
            insurance.put("target", 1000);
            insurance.put("actual", 500);
            insurance.put("percentage", (insurance.get("actual").asInt() / insurance.get("target").asInt()) * 100);
            list.add(insurance);
            ObjectNode creditCards = mapper.createObjectNode();
            creditCards.put("target", 1000);
            creditCards.put("actual", 500);
            creditCards.put("percentage", (creditCards.get("actual").asInt() / creditCards.get("target").asInt()) * 100);
            list.add(creditCards);
            ObjectNode vooma = mapper.createObjectNode();
            vooma.put("target", 1000);
            vooma.put("actual", 500);
            vooma.put("percentage", (vooma.get("actual").asInt() / vooma.get("target").asInt()) * 100);
            list.add(vooma);
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading DSR Summary", e);
        }
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
}
