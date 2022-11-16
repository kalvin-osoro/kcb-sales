package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringQuestionnaireQuestionEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingLeadEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingQuestionerResponseEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingVisitEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.AgencyBankingLeadRepository;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.AgencyBankingQuestionerResponseRepository;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.AgencyBankingVisitRepository;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyCustomerVisitQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyLeadRequest;
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
@RequiredArgsConstructor

@Service
public class AgencyPortalService implements IAgencyPortalService {
    private  final AgencyBankingVisitRepository agencyBankingVisitRepository;
    private final AgencyBankingQuestionerResponseRepository agencyBankingQuestionerResponseRepository;
    private final AgencyBankingLeadRepository agencyBankingLeadRepository;
    @Override
    public List<ObjectNode> getAllCustomerVisits() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyBankingVisitEntity agencyBankingVisitEntity : agencyBankingVisitRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", agencyBankingVisitEntity.getId());
                node.put("merchantName", agencyBankingVisitEntity.getMerchantName());
                node.put("dsrName", agencyBankingVisitEntity.getDsrName());
                node.put("reasonForVisit", agencyBankingVisitEntity.getReasonForVisit());
                node.put("visitDate", agencyBankingVisitEntity.getVisitDate());
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }

    @Override
    public boolean scheduleCustomerVisit(AgencyCustomerVisitsRequest agencyCustomerVisitsRequest) {
        try {
            if (agencyCustomerVisitsRequest == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            AgencyBankingVisitEntity agencyBankingVisitEntity = new AgencyBankingVisitEntity();
            agencyBankingVisitEntity.setMerchantName(agencyCustomerVisitsRequest.getMerchantName());
            agencyBankingVisitEntity.setVisitDate(agencyCustomerVisitsRequest.getVisitDate());
            agencyBankingVisitEntity.setReasonForVisit(agencyCustomerVisitsRequest.getReasonForVisit());
            agencyBankingVisitEntity.setStatus(Status.ACTIVE);
            agencyBankingVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            agencyBankingVisitEntity.setDsrName(agencyCustomerVisitsRequest.getDsrName());
            //save
            agencyBankingVisitRepository.save(agencyBankingVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public boolean reScheduleCustomerVisit(AgencyCustomerVisitsRequest agencyCustomerVisitsRequest) {
        try {
            if (agencyCustomerVisitsRequest == null) {
                return false;
            }
            AgencyBankingVisitEntity agencyBankingVisitEntity = agencyBankingVisitRepository.findById(agencyCustomerVisitsRequest.getId()).orElse(null);
            if (agencyBankingVisitEntity == null) {
                return false;
            }
            agencyBankingVisitEntity.setVisitDate(agencyCustomerVisitsRequest.getVisitDate());
            agencyBankingVisitRepository.save(agencyBankingVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while rescheduling customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getCustomerVisitQuestionnaireResponse(AgencyCustomerVisitQuestionnaireRequest agencyCustomerVisitQuestionnaireRequest) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyBankingQuestionerResponseEntity agencyBankingQuestionerResponseEntity : agencyBankingQuestionerResponseRepository.findAllByVisitIdAndQuestionId(agencyCustomerVisitQuestionnaireRequest.getVisitId(), agencyCustomerVisitQuestionnaireRequest.getQuestionId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", agencyBankingQuestionerResponseEntity.getId());
                node.put("visitId", agencyBankingQuestionerResponseEntity.getVisitId());
                node.put("questionId", agencyBankingQuestionerResponseEntity.getQuestionId());
                node.put("response", agencyBankingQuestionerResponseEntity.getResponse());
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getAllLeads() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyBankingLeadEntity agencyBankingLeadEntity : agencyBankingLeadRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", agencyBankingLeadEntity.getId());
                node.put("customerId", agencyBankingLeadEntity.getCustomerId());
                node.put("businessUnit", agencyBankingLeadEntity.getBusinessUnit());
                node.put("leadStatus", String.valueOf(agencyBankingLeadEntity.getLeadStatus()));
                node.put("topic", agencyBankingLeadEntity.getTopic());
                node.put("priority", agencyBankingLeadEntity.getPriority().ordinal());
                node.put("dsrId", agencyBankingLeadEntity.getDsrId());
                //add to list
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }

    @Override
    public boolean assignLeadToDsr(AgencyAssignLeadRequest agencyAssignLeadRequest, Long leadId) {
        try {
            AgencyBankingLeadEntity agencyBankingLeadEntity = agencyBankingLeadRepository.findById(leadId).get();
            agencyBankingLeadEntity.setDsrId(agencyAssignLeadRequest.getDsrId());
            //set start date from input
            agencyBankingLeadEntity.setStartDate(agencyAssignLeadRequest.getStartDate());
            agencyBankingLeadEntity.setEndDate(agencyAssignLeadRequest.getEndDate());
            //save
            agencyBankingLeadRepository.save(agencyBankingLeadEntity);
            //update is assigned to true
            agencyBankingLeadEntity.setAssigned(true);
            return true;


        } catch (Exception e) {
            log.error("Error assigning lead to dsr", e);
        }
        return false;
    }


}
