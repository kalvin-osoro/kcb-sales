package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingVisitEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingLeadEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingQuestionerResponseEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingVisitEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository.PSBankingCustomerVisitRepository;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository.PSBankingLeadRepository;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository.PSBankingQuestionerResponseRepository;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsRequest;
import com.ekenya.rnd.backend.utils.Status;
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
public class PBPortalService implements IPBPortalService {
    private final PSBankingLeadRepository psBankingLeadRepository;
    
    private final PSBankingCustomerVisitRepository psBankingCustomerVisitRepository;
    private final PSBankingQuestionerResponseRepository psBankingQuestionerResponseRepository;

    @Override
    public List<ObjectNode> getAllLeads() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingLeadEntity psBankingLeadEntity :psBankingLeadRepository.findAll()){
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id",psBankingLeadEntity.getId());
                objectNode.put("customerID",psBankingLeadEntity.getCustomerId());
                objectNode.put("businessUnit",psBankingLeadEntity.getBusinessUnit());
                objectNode.put("leadStatus", psBankingLeadEntity.getLeadStatus().ordinal());
                objectNode.put("topic",psBankingLeadEntity.getTopic());
                objectNode.put("priority", psBankingLeadEntity.getPriority().ordinal());
                objectNode.put("dsrId", psBankingLeadEntity.getDsrId());
                list.add(objectNode);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all leads", e);

        }
        return null;
    }

    @Override
    public boolean assignLead(PBAssignLeadRequest model) {
        try {
            PSBankingLeadEntity psBankingLeadEntity = psBankingLeadRepository.findById(model.getLeadId()).get();
            psBankingLeadEntity.setDsrId(model.getDsrId());
            psBankingLeadEntity.setStartDate(model.getStartDate());
            psBankingLeadEntity.setEndDate(model.getEndDate());
            psBankingLeadEntity.setAssigned(true);

            psBankingLeadRepository.save(psBankingLeadEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while assigning lead", e);
        }
        return false;
    }

    @Override
    public boolean scheduleCustomerVisit(PBCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            PSBankingVisitEntity psBankingVisitEntity = new PSBankingVisitEntity();
            psBankingVisitEntity.setMerchantName(model.getMerchantName());
            psBankingVisitEntity.setVisitDate(model.getVisitDate());
            psBankingVisitEntity.setReasonForVisit(model.getReasonForVisit());
            psBankingVisitEntity.setStatus(Status.ACTIVE);
            psBankingVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            psBankingVisitEntity.setDsrName(model.getDsrName());
            //save
            psBankingCustomerVisitRepository.save(psBankingVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
        }

    @Override
    public boolean rescheduleCustomerVisit(PBCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            PSBankingVisitEntity psBankingVisitEntity = psBankingCustomerVisitRepository.findById(model.getId()).orElse(null);
            if (psBankingVisitEntity == null) {
                return false;
            }
            psBankingVisitEntity.setVisitDate(model.getVisitDate());
            psBankingCustomerVisitRepository.save(psBankingVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while rescheduling customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllCustomerVisits() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingVisitEntity psBankingVisitEntity : psBankingCustomerVisitRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psBankingVisitEntity.getId());
                objectNode.put("customerName", psBankingVisitEntity.getMerchantName());
                objectNode.put("visitDate", psBankingVisitEntity.getVisitDate());
                objectNode.put("reasonForVisit", psBankingVisitEntity.getReasonForVisit());
                objectNode.put("dsrName", psBankingVisitEntity.getDsrName());
                list.add(objectNode);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all customer visits", e);

        }
        return null;
    }

    @Override
    public List<ObjectNode> getCustomerVisitQuestionnaireResponses(PBCustomerVisitQuestionnaireRequest assetManagementRequest) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingQuestionerResponseEntity psBankingQuestionerResponseEntity : psBankingQuestionerResponseRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psBankingQuestionerResponseEntity.getId());
                objectNode.put("visitId", psBankingQuestionerResponseEntity.getVisitId());
                objectNode.put("questionId", psBankingQuestionerResponseEntity.getVisitId());
                objectNode.put("response", psBankingQuestionerResponseEntity.getResponse());
                list.add(objectNode);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all customer visits", e);

        }
        return null;
    }
}

        